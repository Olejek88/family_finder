package ru.shtrm.familyfinder.services

import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.support.v4.content.FileProvider
import android.widget.Toast
import ru.shtrm.familyfinder.R
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

class Downloader(private val dialog: AlertDialog) : AsyncTask<String, Int, String>() {
    private var outputFile: File? = null

    /* (non-Javadoc)
     * @see android.os.AsyncTask#doInBackground(Params[])
     */
    override fun doInBackground(vararg params: String): String? {

        val url: URL
        var connection: HttpURLConnection? = null
        var outputStream: OutputStream? = null
        var inputStream: InputStream? = null

        try {
            url = URL(params[0])
            connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connect()

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val fileLength = connection.contentLength
                outputFile = File(params[1])
                outputStream = FileOutputStream(outputFile!!)
                inputStream = connection.inputStream

                val buffer = ByteArray(1024)
                var readLen: Int
                var total: Long = 0
                while (true) {
                    readLen = inputStream!!.read(buffer)
                    if (isCancelled || readLen == -1) {
                        break
                    }

                    total += readLen.toLong()
                    outputStream.write(buffer, 0, readLen)

                    if (fileLength > 0) {
                        val progress = (total * 100 / fileLength).toInt()
                        //                        Log.d("xxxx", "total: " + total + ", progress: " + progress);
                        publishProgress(progress)
                    }
                }

                return null
            } else {
                Toast.makeText(dialog.context, dialog.context.getString(R.string.update_error), Toast.LENGTH_LONG).show()
                return HttpURLConnection.HTTP_OK.toString()
            }
        } catch (e: IOException) {
            return e.toString()
        } finally {
            try {
                outputStream?.close()
                inputStream?.close()
            } catch (e: IOException) {
            }

            connection?.disconnect()
        }
    }

    /* (non-Javadoc)
     * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
     */
    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        val context = dialog.context
        dialog.dismiss()
        if (result == null && isAPK(outputFile)) {
            val intent: Intent
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                val apkUri = FileProvider.getUriForFile(context,
                        context.packageName + ".provider", outputFile!!)
                intent = Intent(Intent.ACTION_VIEW)
                intent.setDataAndType(apkUri, "application/vnd.android.package-archive")
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                val resInfoList = context.packageManager
                        .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)

                for (resolveInfo in resInfoList) {
                    val packageName = resolveInfo.activityInfo.packageName
                    context.grantUriPermission(packageName, apkUri,
                            Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }

            } else {
                intent = Intent(Intent.ACTION_VIEW)
                intent.setDataAndType(Uri.fromFile(outputFile), "application/vnd.android.package-archive")
            }

            context.startActivity(intent)
        } else {
            //Toast.makeText(dialog.context, dialog.context.getString(R.string.update_error), Toast.LENGTH_LONG).show()
        }
    }

    companion object {

        fun isAPK(file: File?): Boolean {
            var fis: FileInputStream?
            var zipIs: ZipInputStream?
            var zEntry: ZipEntry?
            val dexFile = "classes.dex"
            val manifestFile = "AndroidManifest.xml"
            var hasDex = false
            var hasManifest = false

            try {
                fis = FileInputStream(file!!)
                zipIs = ZipInputStream(BufferedInputStream(fis))
                while (true) {
                    zEntry = zipIs.nextEntry
                    if (zEntry==null)
                        break
                    if (zEntry!!.name.equals(dexFile, ignoreCase = true)) {
                        hasDex = true
                    } else if (zEntry.name.equals(manifestFile, ignoreCase = true)) {
                        hasManifest = true
                    }
                    if (hasDex && hasManifest) {
                        zipIs.close()
                        fis.close()
                        return true
                    }
                }
                zipIs.close()
                fis.close()
            } catch (e: FileNotFoundException) {
                return false
            } catch (e: IOException) {
                return false
            }

            return false
        }
    }
}
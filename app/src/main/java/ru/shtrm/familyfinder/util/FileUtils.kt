package ru.shtrm.familyfinder.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.os.Environment
import android.util.Log
import android.widget.Toast
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.min

object FileUtils {

    @Throws(IOException::class)
    fun loadJSONFromAsset(context: Context, jsonFileName: String): String {
        (context.assets).open(jsonFileName).let {
            val buffer = ByteArray(it.available())
            it.read(buffer)
            it.close()
            return String(buffer, Charset.forName("UTF-8"))
        }
    }

    fun getBitmapByPath(path: String, filename: String): Bitmap? {
        val imageFull = File(path + filename)
        if(imageFull.isFile)
            return BitmapFactory.decodeFile(imageFull.absolutePath)
        else
            return null
    }

    fun getPicturesDirectory(context: Context): String {
        return (Environment.getExternalStorageDirectory().toString()
                + "/Android/data/"
                + context.packageName
                + "/Files"
                + File.separator)
    }

    fun storeNewImage(image: Bitmap?, context: Context, width: Int, image_name: String?): Bitmap? {
        var imageName = image_name
        val mediaStorageDir = File(getPicturesDirectory(context))

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                if (isExternalStorageWritable()) {
                    Toast.makeText(context, "No data acceess",
                            Toast.LENGTH_LONG).show()
                    return null
                }
            }
        }
        if (image_name == null || image_name == "") {
            val timeStamp = SimpleDateFormat("ddMMyyyy_HHmm", Locale.US).format(Date())
            imageName = "file_$timeStamp.jpg"
        }
        // Create a media file name
        val pictureFile: File
        pictureFile = File(mediaStorageDir.path + File.separator + imageName)

        try {
            val fos = FileOutputStream(pictureFile)
            if (image != null) {
                val dimension = min(image.height,image.width)
                if (dimension > 0) {
                    val myBitmap = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
                    myBitmap.compress(Bitmap.CompressFormat.JPEG, 85, fos)
                    fos.flush()
                    fos.close()
                    return myBitmap
                }
            }
        } catch (e: FileNotFoundException) {
            Log.d("Util", "File not found: " + e.message)
        } catch (e: IOException) {
            Log.d("Util", "Error accessing file: " + e.message)
        }

        return null
    }

    private fun isExternalStorageWritable(): Boolean {
        val state = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == state
    }

}

package ru.shtrm.familyfinder.util

import android.app.ProgressDialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.graphics.drawable.toDrawable
import ru.shtrm.familyfinder.R


object CommonUtil {

    fun showLoadingDialog(context: Context?): ProgressDialog {

        val progressDialog = ProgressDialog(context)
        progressDialog.let {
            it.show()
            it.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
            it.setContentView(R.layout.progress_dialog)
            it.isIndeterminate = true
            it.setCancelable(false)
            it.setCanceledOnTouchOutside(false)
            return it
        }
    }

    fun showProgressBar(context: Context?, window: Window): ProgressBar {
        val progressBar = ProgressBar(context, null, android.R.attr.progressBarStyleLarge)
        val params = RelativeLayout.LayoutParams(100, 100)
        params.addRule(RelativeLayout.CENTER_IN_PARENT)
        progressBar.let {
            it.visibility = View.VISIBLE
            it.isIndeterminate = true
            it.progressTintList = ColorStateList.valueOf(Color.RED)
            window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            return it
        }
    }
}

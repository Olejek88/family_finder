package ru.shtrm.familyfinder.util

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ProgressBar

object CommonUtil {

    fun showProgressBar(window: Window, progressBar: ProgressBar) {
        progressBar.visibility = View.VISIBLE
        progressBar.progressTintList = ColorStateList.valueOf(Color.RED)
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }
}

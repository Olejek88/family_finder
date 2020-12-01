package ru.shtrm.familyfinder.util

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.Gravity

class IconTextView : android.support.v7.widget.AppCompatTextView {

    private var cont: Context? = null

    constructor(context: Context) : super(context) {
        this.cont = context
        createView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.cont = context
        createView()
    }

    private fun createView() {
        gravity = Gravity.CENTER
        typeface = Typeface.createFromAsset(cont!!.assets, "FontAwesomeSolid.otf")
    }
}
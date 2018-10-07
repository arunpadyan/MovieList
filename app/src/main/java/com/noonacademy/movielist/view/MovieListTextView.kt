package com.noonacademy.movielist.view

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import com.noonacademy.movielist.MovieApplication
import com.noonacademy.movielist.R

class MovieListTextView:TextView{

    val FONT_REGULAR = 0x00
    val FONT_REGULAR_MEDIUM = 0x01
    val FONT_REGULAR_BOLD = 0x02

    lateinit var mApp: MovieApplication

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        mApp = context.applicationContext as MovieApplication
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.MovieListTextView)
            val fontName = a.getInteger(R.styleable.MovieListTextView_fontName, FONT_REGULAR)
            setFont(fontName)
            a.recycle()
        } else
            typeface = mApp.getFontRegular()
    }

    fun setFont(fontName: Int) {
        when (fontName) {
            FONT_REGULAR -> typeface = mApp.getFontRegular()
            FONT_REGULAR_MEDIUM -> typeface = mApp.getFontBold()
            FONT_REGULAR_BOLD -> typeface = mApp.getFontMedium()
            else -> typeface = mApp.getFontRegular()
        }
    }


}
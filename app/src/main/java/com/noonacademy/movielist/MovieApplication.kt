package com.noonacademy.movielist

import android.app.Application
import android.graphics.Typeface
import androidx.multidex.MultiDexApplication
import com.noonacademy.movielist.data.network.ApiClient

class MovieApplication : MultiDexApplication() {

    private val mFontRegular: Typeface  by lazy { Typeface.createFromAsset(this.assets, "fonts/Font-Regular.ttf") }
    private val mFontRegularBold: Typeface by lazy { Typeface.createFromAsset(this.assets, "fonts/Font-Bold.ttf") }
    private val mFontMedium: Typeface by lazy { Typeface.createFromAsset(this.assets, "fonts/Font-Medium.ttf") }

    override fun onCreate() {
        super.onCreate()
        ApiClient.initAliClient(this)
    }

    fun getFontRegular() = mFontRegular

    fun getFontMedium() = mFontMedium

    fun getFontBold() = mFontRegularBold

}
package com.noonacademy.movielist

import android.app.Application
import android.graphics.Typeface
import androidx.multidex.MultiDexApplication
import com.noonacademy.movielist.data.network.ApiClient

class MovieApplication: MultiDexApplication() {

    private var mFontRegular: Typeface? = null
    private var mFontRegularBold: Typeface? = null
    private var mFontMedium: Typeface? = null

    override fun onCreate() {
        super.onCreate()
        ApiClient.initAliClient(this)
    }

    fun getFontRegular(): Typeface {
        if (mFontRegular == null) {
            mFontRegular = Typeface.createFromAsset(this.assets, "fonts/Font-Regular.ttf")
        }
        return mFontRegular!!
    }

    fun getFontMedium(): Typeface {
        if (mFontMedium == null) {
            mFontMedium = Typeface.createFromAsset(this.assets, "fonts/Font-Medium.ttf")
        }
        return mFontMedium!!
    }

    fun getFontBold(): Typeface {
        if (mFontRegularBold == null) {
            mFontRegularBold = Typeface.createFromAsset(this.assets, "fonts/Font-Bold.ttf")
        }
        return mFontRegularBold!!
    }

}
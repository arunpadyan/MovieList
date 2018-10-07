package com.noonacademy.movielist.utils

import android.view.View
import com.google.gson.Gson

fun Any.toJSONString(): String {
    return Gson().toJson(this)
}
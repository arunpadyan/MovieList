package com.noonacademy.movielist.data.network

import android.content.Context

import com.google.gson.Gson
import com.noonacademy.movielist.BuildConfig
import com.noonacademy.movielist.R

import java.io.IOException
import java.util.concurrent.TimeUnit

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object ApiClient {

    private var mInterface: MovieApi? = null

    private fun initAliClient(url: String, context: Context, gson: Gson): Retrofit {

        val builder = OkHttpClient.Builder()

        val headerInterceptor = Interceptor { chain ->
            var request = chain.request()
            val urlBuilder = request.url().newBuilder()
                    .addQueryParameter("apikey", context.getString(R.string.omdbapi_api_key))

            request = request.newBuilder().url(urlBuilder.build()).build()
            chain.proceed(request)
        }


        builder.addInterceptor(headerInterceptor)
        builder.connectTimeout(10, TimeUnit.MINUTES)
        builder.readTimeout(10, TimeUnit.MINUTES)
        builder.writeTimeout(10, TimeUnit.MINUTES)


        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(httpLoggingInterceptor)
        }

        return Retrofit.Builder()
                .baseUrl(url)
                .client(builder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }

    fun initAliClient(context: Context): MovieApi? {
        if (mInterface == null) {
            mInterface = initAliClient(BuildConfig.BASE_URL, context, Gson()).create(MovieApi::class.java)
        }
        return mInterface
    }

    fun instance(): MovieApi? {
        return mInterface
    }

}


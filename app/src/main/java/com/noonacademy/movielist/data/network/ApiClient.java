package com.noonacademy.movielist.data.network;

import android.content.Context;

import com.google.gson.Gson;
import com.noonacademy.movielist.BuildConfig;
import com.noonacademy.movielist.R;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

    private static MovieApi mInterface;

    private static Retrofit initAliClient(String url, final Context context, Gson gson) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl.Builder urlBuilder = request.url().newBuilder()
                        .addQueryParameter("apikey", context.getString(R.string.omdbapi_api_key));

                request = request.newBuilder().url(urlBuilder.build()).build();
                return chain.proceed(request);
            }
        };


        builder.addInterceptor(headerInterceptor);
        builder.connectTimeout(10, TimeUnit.MINUTES);
        builder.readTimeout(10, TimeUnit.MINUTES);
        builder.writeTimeout(10, TimeUnit.MINUTES);


        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(httpLoggingInterceptor);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(builder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit;
    }

    public static MovieApi initAliClient(Context context) {
        if (mInterface == null) {
            mInterface = initAliClient(BuildConfig.BASE_URL, context, new Gson()).create(MovieApi.class);
        }
        return mInterface;
    }

    public static MovieApi instance() {
        return mInterface;
    }

}


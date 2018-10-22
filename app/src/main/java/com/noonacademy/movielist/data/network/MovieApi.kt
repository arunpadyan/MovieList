package com.noonacademy.movielist.data.network

import com.noonacademy.movielist.data.network.response.MovieListResponse
import com.noonacademy.movielist.models.Movie

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("/")
    fun getMovieList(@Query("page") mPage: Int, @Query("s") mQuery: String): Observable<MovieListResponse>

    @GET("/")
    fun getMovieDetails(@Query("i") mImdbId: String): Observable<Movie>

}

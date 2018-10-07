package com.noonacademy.movielist.data.network;

import com.noonacademy.movielist.data.network.response.MovieListResponse;
import com.noonacademy.movielist.models.Movie;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApi {

    @GET("/")
    Observable<MovieListResponse> getMovieList(@Query("page") int mPage,@Query("s") String mQuery);

    @GET("/")
    Observable<Movie> getMovieDetails(@Query("i") String mImdbId);

}

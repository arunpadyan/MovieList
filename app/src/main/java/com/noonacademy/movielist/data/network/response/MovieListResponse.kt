package com.noonacademy.movielist.data.network.response

import com.google.gson.annotations.SerializedName
import com.noonacademy.movielist.models.Movie

class MovieListResponse(@SerializedName("Search") var mSearchResult:ArrayList<Movie>?)
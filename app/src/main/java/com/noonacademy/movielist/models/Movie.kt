package com.noonacademy.movielist.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity
data class Movie(
        @ColumnInfo(name = "title")
        @SerializedName("Title") var mTitle: String,

        @ColumnInfo(name = "year")
        @SerializedName("Year") var mYear: String,

        @PrimaryKey
        @ColumnInfo(name = "imdb_id")
        @SerializedName("imdbID") var mImdbID: String,

        @ColumnInfo(name = "poster")
        @SerializedName("Poster") var mPoster: String,

        @ColumnInfo(name = "runtime")
        @SerializedName("Runtime") var mRuntime: String?,

        @ColumnInfo(name = "genre")
        @SerializedName("Genre") var mGenre: String?,

        @ColumnInfo(name = "director")
        @SerializedName("Director") var mDirector: String?,

        @ColumnInfo(name = "actors")
        @SerializedName("Actors") var mActors: String?,

        @ColumnInfo(name = "plot")
        @SerializedName("Plot") var mPlot: String?,

        @ColumnInfo(name = "imdb_rating")
        @SerializedName("imdbRating") var mImdbRating: String?,

        @ColumnInfo(name = "imdb_votes")
        @SerializedName("imdbVotes") var mImdbVotes: String?,

        @ColumnInfo(name = "response")
        @SerializedName("Response") var mResponse: String?,
        var isBookmarked: Boolean = false
)
package com.noonacademy.movielist.data.db.fav_movies

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.noonacademy.movielist.models.Movie
import io.reactivex.Observable

@Dao
interface FavMoviesDao {

    @Query("SELECT * FROM movie")
    fun getFavMovies(): Observable<List<Movie>>

    @Insert(onConflict = REPLACE)
    fun saveToFavMovie(mMovie: Movie)

    @Delete
    fun removeFromFavMovies(mMovie: Movie)

}
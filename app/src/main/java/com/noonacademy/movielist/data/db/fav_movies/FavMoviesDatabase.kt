package com.noonacademy.movielist.data.db.fav_movies

import androidx.room.Database
import androidx.room.RoomDatabase
import com.noonacademy.movielist.models.Movie

@Database(entities = [(Movie::class)], version = 1, exportSchema = false)
abstract class FavMoviesDatabase : RoomDatabase() {
    abstract fun favDao(): FavMoviesDao
}

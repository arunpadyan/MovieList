package com.noonacademy.movielist.ui.movie_details

import android.content.Context
import androidx.room.Room
import com.noonacademy.movielist.data.db.fav_movies.FavMoviesDatabase
import com.noonacademy.movielist.data.network.ApiClient
import com.noonacademy.movielist.models.Movie
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieDetailsInteractor(mContext: Context) : MovieDetailsContract.Interactor {

    var mFavDatabase: FavMoviesDatabase = Room.databaseBuilder(mContext, FavMoviesDatabase::class.java, "fav-movie-database").build()

    override fun getDetails(mId: String,
                            mOnFinishedListener: MovieDetailsContract.OnFinishedListener<Movie>) {
        var disposable = ApiClient.instance()
                .getMovieDetails(mId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mOnFinishedListener.onFinished(it)
                    mOnFinishedListener.onComplete()
                }, {
                    mOnFinishedListener.onFailure(it)
                    mOnFinishedListener.onComplete()
                })
    }

    override fun addToFav(mMovie: Movie) {
        var disposable = Observable.just<Movie>(mMovie)
                .subscribeOn(Schedulers.io())
                .subscribe { mMovie1 -> mFavDatabase.favDao().saveToFavMovie(mMovie1) }
    }

    override fun removeFromFav(mMovie: Movie) {
        var disposable = Observable.just<Movie>(mMovie)
                .subscribeOn(Schedulers.io())
                .subscribe { mMovie1 -> mFavDatabase.favDao().removeFromFavMovies(mMovie1) }
    }

}
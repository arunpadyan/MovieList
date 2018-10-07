package com.noonacademy.movielist.ui.movie_list

import android.content.Context
import androidx.room.Room
import com.noonacademy.movielist.data.db.fav_movies.FavMoviesDatabase
import com.noonacademy.movielist.data.network.ApiClient
import com.noonacademy.movielist.data.network.response.MovieListResponse
import com.noonacademy.movielist.models.Movie
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MovieListInteractor(var mContext: Context) : MovieListContract.Interactor {

    var mFavDatabase: FavMoviesDatabase = Room.databaseBuilder(mContext, FavMoviesDatabase::class.java, "fav-movie-database").build()
    var favListDisposable: Disposable? = null

    override fun getSearchResult(mQuery: String,
                                 mPage: Int,
                                 mOnFinishedListener: MovieListContract.OnFinishedListener<MovieListResponse>) {

        var disposable = ApiClient.instance()
                .getMovieList(mPage, mQuery)
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

    override fun getFavList(mOnFinishedListener: MovieListContract.OnFinishedListener<ArrayList<Movie>>) {
        if (favListDisposable != null)
            favListDisposable?.dispose()
        favListDisposable = mFavDatabase.favDao().getFavMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mOnFinishedListener.onFinished(ArrayList(it))
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
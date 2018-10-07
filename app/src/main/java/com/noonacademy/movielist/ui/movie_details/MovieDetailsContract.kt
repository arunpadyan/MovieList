package com.noonacademy.movielist.ui.movie_details

import com.noonacademy.movielist.models.Movie

class MovieDetailsContract {

    interface View {
        fun setName(mName: String)
        fun setYear(mYear: String)
        fun setDirector(mDirector: String)
        fun setImage(mImage: String)
        fun setGenre(mGenre: String)
        fun setImdbRating(mRating: String)
        fun setActors(mActors: String)
        fun showToast(mToast: String)
        fun startShimmer()
        fun stopShimmer()
        fun setIsLiked(isLiked:Boolean)
    }

    interface Presenter {
        fun onLiked()
        fun onUnliked()
    }

    interface Interactor {
        fun getDetails(mId: String, mOnFinishedListener: OnFinishedListener<Movie>)
        fun addToFav(mMovie: Movie)
        fun removeFromFav(mMovie: Movie)
    }

    interface OnFinishedListener<T> {
        fun onFinished(mResult: T)
        fun onFailure(t: Throwable)
        fun onComplete()
    }
}
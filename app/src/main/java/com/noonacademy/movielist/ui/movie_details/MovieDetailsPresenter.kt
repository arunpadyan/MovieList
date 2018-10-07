package com.noonacademy.movielist.ui.movie_details

import com.noonacademy.movielist.models.Movie

class MovieDetailsPresenter(var mView: MovieDetailsContract.View,
                            var mInteractor: MovieDetailsContract.Interactor,
                            val mMovie: Movie) : MovieDetailsContract.Presenter {

    init {
        mView.setImage(mMovie.mPoster)
        mView.setName(mMovie.mTitle)
        mView.setYear(mMovie.mYear)
        mView.setIsLiked(mMovie.isBookmarked)
        loadData()
    }

    private fun loadData() {
        mView.startShimmer()
        mInteractor.getDetails(mMovie.mImdbID, object : MovieDetailsContract.OnFinishedListener<Movie> {
            override fun onFinished(mResult: Movie) {
                mView.setActors("Actors : ${mResult.mActors!!}")
                mView.setDirector("Director : ${mResult.mDirector!!}")
                mView.setGenre("${mResult.mGenre!!}")
                mView.setImdbRating("Ra ting : " + mResult.mImdbRating!! + "/10")
            }

            override fun onFailure(t: Throwable) {
                mView.showToast(t.message ?: "Something went wrong please try again later...")
            }

            override fun onComplete() {
                mView.stopShimmer()
            }
        })
    }

    override fun onLiked() {
        mInteractor.addToFav(mMovie)
    }

    override fun onUnliked() {
        mInteractor.removeFromFav(mMovie)
    }
}
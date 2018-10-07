package com.noonacademy.movielist.ui.movie_list

import com.noonacademy.movielist.data.network.response.MovieListResponse
import com.noonacademy.movielist.models.Movie
import com.noonacademy.movielist.ui.movie_list.main_list.Action

class MovieListContract{

    interface View{
        fun setAdapter(mActionCallBack: (mMovie: Movie, mAction: Action) -> Unit)
        fun updateSearchResultList(mResult: ArrayList<Any>)
        fun getSearchFieldText(): String
        fun setSearchFieldText(mText:String)
        fun runLambdaOnUiThread(lambdaFiction: () -> Unit)
        fun startDetailsActivity(mMovie:Movie)
    }

    interface Presenter{
        fun initPresenter()
        fun searchTextChanged(mSearchText:String)
        fun onListEndReached()
        fun onResume()
    }

    interface Interactor{
        fun getSearchResult(mQuery:String,mPage:Int,mOnFinishedListener:OnFinishedListener<MovieListResponse>)
        fun getFavList(mOnFinishedListener:OnFinishedListener<ArrayList<Movie>>)
        fun addToFav(mMovie:Movie)
        fun removeFromFav(mMovie:Movie)
    }

    interface OnFinishedListener<T> {
        fun onFinished(mResult: T)
        fun onFailure(t: Throwable)
        fun onComplete()
    }
}
package com.noonacademy.movielist.ui.movie_list

import android.util.Log
import com.noonacademy.movielist.data.network.response.MovieListResponse
import com.noonacademy.movielist.models.ListItemBookmarks
import com.noonacademy.movielist.models.ListItemShimmer
import com.noonacademy.movielist.models.Movie
import com.noonacademy.movielist.ui.movie_list.main_list.ACTION_LIKE
import com.noonacademy.movielist.ui.movie_list.main_list.ACTION_TAP
import com.noonacademy.movielist.ui.movie_list.main_list.ACTION_UNLIKE
import com.noonacademy.movielist.ui.movie_list.main_list.Action
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.timerTask

class MovieListPresenter(var mView: MovieListContract.View, var mInstructor: MovieListContract.Interactor) : MovieListContract.Presenter {

    val DEFAULT_QUERY = "Game"
    val SEARCH_THROTL_DURATION = 700L

    var searchStack: Stack<String> = Stack()
    var mMovieList: ArrayList<Movie> = ArrayList()
    var mSearchList: ArrayList<Any> = ArrayList()
    var mBookmarkedMovieList: ArrayList<Movie> = ArrayList()
    var isSearching = false
    var endReached = false
    var mLastPageLoaded = 0
    var query = ""
    
    override fun initPresenter() {
        mView.setAdapter(mActionCallBack)
        mView.setSearchFieldText(DEFAULT_QUERY)
    }

    var mActionCallBack = fun(mMovie: Movie, mAction: Action) {
        when (mAction.mAction) {
            ACTION_LIKE -> {
                mInstructor.addToFav(mMovie)
            }
            ACTION_UNLIKE -> {
                mInstructor.removeFromFav(mMovie)
            }
            ACTION_TAP -> {
                mView.startDetailsActivity(mMovie)
            }
        }
    }

    override fun onResume() {
        loadBookmarkedMovies()
    }

    private fun loadBookmarkedMovies() {
        mInstructor.getFavList(object : MovieListContract.OnFinishedListener<ArrayList<Movie>> {
            override fun onFinished(mResult: ArrayList<Movie>) {
                mBookmarkedMovieList = mResult
                processBookmarkedMovieList()
                publishSearchResult()
            }

            override fun onFailure(t: Throwable) {
            }

            override fun onComplete() {
            }

        })
    }

    override fun searchTextChanged(mSearchText: String) {
        searchStack.push(mSearchText)
        Timer().schedule(timerTask {
            if (searchStack.isNotEmpty())
                if (searchStack.peek() == mSearchText)
                    if (!isSearching) {
                        mView.runLambdaOnUiThread {

                            mMovieList.clear()
                            query = searchStack.pop()
                            searchStack.clear()

                            mLastPageLoaded = 1

                            doSearchNetworkRequest(mLastPageLoaded)
                        }
                    }
        }, SEARCH_THROTL_DURATION)
    }

    override fun onListEndReached() {
        if (!isSearching && mLastPageLoaded != 0 && !endReached) {
            mLastPageLoaded++
            doSearchNetworkRequest(mLastPageLoaded)
        }
    }

    private fun doSearchNetworkRequest(mPage: Int) {
        isSearching = true
        endReached = false
        setAsLoading()
        mInstructor.getSearchResult(query, mPage,
                object : MovieListContract.OnFinishedListener<MovieListResponse> {
                    override fun onFinished(mResult: MovieListResponse) {
                        if (mResult.mSearchResult.orEmpty().isEmpty()) {
                            endReached = true
                        } else {
                            mMovieList.addAll(mResult.mSearchResult!!)
                            processBookmarkedMovieList()
                        }
                        publishSearchResult()
                    }

                    override fun onFailure(t: Throwable) {
                        mLastPageLoaded--
                    }

                    override fun onComplete() {
                        isSearching = false
                    }

                })
    }


    private fun setAsLoading() {
        if (mMovieList.size == 0) {
            mSearchList.clear()
            if (mBookmarkedMovieList.size != 0) {
                mSearchList.add(ListItemBookmarks("Your Bookmarks", mBookmarkedMovieList))
            }
            for (i in 0..10) {
                mSearchList.add(ListItemShimmer(i))
            }
        } else {
            mSearchList.add(ListItemShimmer(0))
        }
        mView.updateSearchResultList(mSearchList)
    }

    fun processBookmarkedMovieList() {
        mMovieList.forEach {
            var item = mBookmarkedMovieList.find { bookmarked -> bookmarked.mImdbID == it.mImdbID }
            it.isBookmarked = (item != null)
        }
    }

    fun publishSearchResult() {
        mSearchList.clear()
        if (mBookmarkedMovieList.size != 0) {
            mSearchList.add(ListItemBookmarks("Your Bookmarks", mBookmarkedMovieList))
        }
        for (mMovie in mMovieList) {
            mSearchList.add(mMovie.copy())
        }
        mView.updateSearchResultList(mSearchList)
    }
}
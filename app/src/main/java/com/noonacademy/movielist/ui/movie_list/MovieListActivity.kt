package com.noonacademy.movielist.ui.movie_list

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.noonacademy.movielist.R
import com.noonacademy.movielist.models.Movie
import com.noonacademy.movielist.ui.movie_list.main_list.Action
import com.noonacademy.movielist.ui.movie_list.main_list.MovieListAdapter
import kotlinx.android.synthetic.main.activity_movie_list.*
import android.nfc.tech.MifareUltralight.PAGE_SIZE
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.noonacademy.movielist.ui.movie_details.MovieDetailsActivity


class MovieListActivity : AppCompatActivity(), MovieListContract.View {


    private lateinit var mPresenter: MovieListContract.Presenter
    private lateinit var mAdapter: MovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)
        initView()
        initPresenter()
    }

    override fun onResume() {
        super.onResume()
        mPresenter.onResume()
    }

    private fun initView() {
        et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mPresenter.searchTextChanged(s.toString())
            }
        })
    }

    private fun initPresenter() {
        var interactor = MovieListInteractor(this)
        mPresenter = MovieListPresenter(this, interactor)
        mPresenter.initPresenter()
    }

    override fun setAdapter(mActionCallBack: (mMovie: Movie, mAction: Action) -> Unit) {
        rv_movie_list.layoutManager = LinearLayoutManager(this)
        rv_movie_list.addOnScrollListener(recyclerViewOnScrollListener)
        (rv_movie_list.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        mAdapter = MovieListAdapter(mActionCallBack)
        rv_movie_list.adapter = mAdapter
    }

    override fun updateSearchResultList(mResult: ArrayList<Any>) {
        mAdapter.updateList(mResult)
    }

    override fun getSearchFieldText(): String {
        return et_search.text.toString()
    }

    override fun setSearchFieldText(mText: String) {
        et_search.setText(mText)
        var textLength = mText.length
        et_search.setSelection(textLength, textLength);
        hideSoftKeyBoard()
    }

    override fun runLambdaOnUiThread(lambdaFiction: () -> Unit) {
        runOnUiThread { lambdaFiction() }
    }

    override fun startDetailsActivity(mMovie: Movie) {
        MovieDetailsActivity.startActivity(this, mMovie)
    }

    private val recyclerViewOnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val visibleItemCount = recyclerView.layoutManager!!.getChildCount()
            val totalItemCount = recyclerView.layoutManager!!.getItemCount()
            val firstVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                    && firstVisibleItemPosition >= 0
                    && totalItemCount >= PAGE_SIZE) {
                mPresenter.onListEndReached()
            }
            if (dy > 0)
                hideSoftKeyBoard()
        }
    }

    fun hideSoftKeyBoard() {
        try {
            val view = et_search
            if (view != null) {
                val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            }
        } catch (e: Exception) {

        }

    }

}

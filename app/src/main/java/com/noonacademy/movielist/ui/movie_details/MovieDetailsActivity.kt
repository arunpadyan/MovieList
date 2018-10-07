package com.noonacademy.movielist.ui.movie_details

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import com.like.LikeButton
import com.like.OnLikeListener
import com.noonacademy.movielist.R
import com.noonacademy.movielist.models.Movie
import com.noonacademy.movielist.utils.toJSONString
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_details.*

class MovieDetailsActivity : AppCompatActivity(), MovieDetailsContract.View {

    companion object {

        val INTENT_EXTRA_MOVIE = "movie"
        fun startActivity(context: Context, mMovie: Movie) {
            var intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra(INTENT_EXTRA_MOVIE, mMovie.toJSONString())
            context.startActivity(intent)
        }
    }

    lateinit var mPresenter: MovieDetailsContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        mPresenter = MovieDetailsPresenter(this,
                MovieDetailsInteractor(this),
                Gson().fromJson(intent.getStringExtra(INTENT_EXTRA_MOVIE), Movie::class.java))

        initView()
    }

    fun initView() {
        iv_back.setOnClickListener { finish() }
        like_button.setOnLikeListener(object : OnLikeListener {
            override fun liked(p0: LikeButton?) {
                mPresenter.onLiked()
            }

            override fun unLiked(p0: LikeButton?) {
                mPresenter.onUnliked()
            }
        })
    }

    override fun setImage(mImage: String) {
        Picasso.with(this).load(mImage).into(iv_big_poster)
        Picasso.with(this).load(mImage).into(iv_poster)
    }

    override fun setName(mName: String) {
        tv_title.text = mName
    }

    override fun setYear(mYear: String) {
        tv_year.text = mYear
    }

    override fun setGenre(mGenre: String) {
        tv_genra.text = mGenre
    }

    override fun setDirector(mDirector: String) {
        tv_director.text = mDirector
    }

    override fun setImdbRating(mRating: String) {
        tv_rating.text = mRating
    }

    override fun setActors(mActors: String) {
        tv_actors.text = mActors
    }

    override fun showToast(mToast: String) {
        Toast.makeText(this, mToast, Toast.LENGTH_LONG).show()
    }

    override fun startShimmer() {
        shimmer_view_container.startShimmerAnimation()
    }

    override fun stopShimmer() {
        shimmer_view_container.stopShimmerAnimation()
    }

    override fun setIsLiked(isLiked: Boolean) {
        like_button.isLiked = isLiked
    }
}

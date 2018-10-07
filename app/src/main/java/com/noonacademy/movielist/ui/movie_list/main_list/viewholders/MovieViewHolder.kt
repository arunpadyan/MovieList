package com.noonacademy.movielist.ui.movie_list.main_list.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.like.LikeButton
import com.like.OnLikeListener
import com.noonacademy.movielist.R
import com.noonacademy.movielist.models.Movie
import com.noonacademy.movielist.ui.movie_list.main_list.ACTION_LIKE
import com.noonacademy.movielist.ui.movie_list.main_list.ACTION_TAP
import com.noonacademy.movielist.ui.movie_list.main_list.ACTION_UNLIKE
import com.noonacademy.movielist.ui.movie_list.main_list.Action
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.main_list_item_movie.view.*

class MovieViewHolder(itemView: View, callBack: (mMovie: Movie, mAction: Action) -> Unit)
    : RecyclerView.ViewHolder(itemView) {

    lateinit var mMovie: Movie

    init {
        itemView.setOnClickListener {
            callBack(mMovie, Action(ACTION_TAP))
        }

        itemView.like_button.setOnLikeListener(object : OnLikeListener {
            override fun liked(p0: LikeButton?) {
                callBack(mMovie, Action(ACTION_LIKE))
            }

            override fun unLiked(p0: LikeButton?) {
                callBack(mMovie, Action(ACTION_UNLIKE))
            }
        })
    }

    fun bind(mNewMovie: Movie) {
        this.mMovie = mNewMovie
        Picasso.with(itemView.context).load(mMovie.mPoster).into(itemView.iv_poster)
        itemView.tv_title.text = mMovie.mTitle
        itemView.tv_year.text = mMovie.mYear
        itemView.like_button.isLiked = mMovie.isBookmarked
    }

    companion object {
        fun create(parent: ViewGroup, callBack: (
                mMovie: Movie,
                mAction: Action
        ) -> Unit): MovieViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.main_list_item_movie, parent, false)
            return MovieViewHolder(view, callBack)
        }
    }

}
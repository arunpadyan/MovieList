package com.noonacademy.movielist.ui.movie_list.main_list.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.noonacademy.movielist.R
import com.noonacademy.movielist.models.ListItemBookmarks
import com.noonacademy.movielist.models.Movie
import com.noonacademy.movielist.ui.movie_list.bookmark_list.BookmarkListAdapter
import com.noonacademy.movielist.ui.movie_list.main_list.Action
import com.noonacademy.movielist.ui.movie_list.main_list.MovieListAdapter
import kotlinx.android.synthetic.main.main_list_item_bookmarks.view.*

class BookmarksViewHolder(itemView: View, callBack: (mMovie: Movie, mAction: Action) -> Unit)
    : RecyclerView.ViewHolder(itemView) {

    private lateinit var mBookmarks: ListItemBookmarks
    var mAdapter = BookmarkListAdapter(callBack)

    init {
        itemView.rv_fav.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
        itemView.rv_fav.adapter = mAdapter
    }

    fun bind(mNewListItemBookmarks: ListItemBookmarks) {
        this.mBookmarks = mNewListItemBookmarks
        itemView.tv_title.text = mBookmarks.mTitle
        mAdapter.updateList(mBookmarks.mBookmarks)
    }

    companion object {
        fun create(parent: ViewGroup, callBack: (
                mMovie: Movie,
                mAction: Action
        ) -> Unit): BookmarksViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.main_list_item_bookmarks, parent, false)
            return BookmarksViewHolder(view, callBack)
        }
    }

}
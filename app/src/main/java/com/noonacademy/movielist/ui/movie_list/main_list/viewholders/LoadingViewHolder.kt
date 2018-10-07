package com.noonacademy.movielist.ui.movie_list.main_list.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.noonacademy.movielist.R
import com.noonacademy.movielist.models.ListItemLoader

class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    lateinit var mListItemLoader: ListItemLoader

    init {

    }

    fun bind(mNewListItemLoader: ListItemLoader) {
        this.mListItemLoader = mNewListItemLoader
    }

    companion object {
        fun create(parent: ViewGroup): LoadingViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.main_list_item_loading, parent, false)
            return LoadingViewHolder(view)
        }
    }

}
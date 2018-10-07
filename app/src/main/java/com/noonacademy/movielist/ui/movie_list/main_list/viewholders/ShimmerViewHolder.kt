package com.noonacademy.movielist.ui.movie_list.main_list.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.noonacademy.movielist.R
import com.noonacademy.movielist.models.ListItemShimmer
import kotlinx.android.synthetic.main.main_list_item_shimmer.view.*

class ShimmerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    lateinit var mListItemShimmer: ListItemShimmer

    init {

    }

    fun bind(mNewShimmerItem: ListItemShimmer) {
        this.mListItemShimmer = mNewShimmerItem
        itemView.shimmer_view_container.startShimmer()
    }

    companion object {
        fun create(parent: ViewGroup): ShimmerViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.main_list_item_shimmer, parent, false)
            return ShimmerViewHolder(view)
        }
    }

}
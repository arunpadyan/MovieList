package com.noonacademy.movielist.ui.movie_list.main_list

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.noonacademy.movielist.models.ListItemBookmarks
import com.noonacademy.movielist.models.ListItemLoader
import com.noonacademy.movielist.models.ListItemShimmer
import com.noonacademy.movielist.models.Movie
import com.noonacademy.movielist.ui.movie_list.main_list.viewholders.BookmarksViewHolder
import com.noonacademy.movielist.ui.movie_list.main_list.viewholders.LoadingViewHolder
import com.noonacademy.movielist.ui.movie_list.main_list.viewholders.MovieViewHolder
import com.noonacademy.movielist.ui.movie_list.main_list.viewholders.ShimmerViewHolder

class MovieListAdapter(private val mActionCallBack: (mMovie: Movie, mAction: Action) -> Unit)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val ITEM_TYPE_BOOKMARKS = 0
    private val ITEM_TYPE_MOVIE = 1
    private val ITEM_TYPE_SHIMMER = 2
    private val ITEM_TYPE_LOADING = 3

    var mListItems = ArrayList<Any>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_TYPE_MOVIE ->
                MovieViewHolder.create(parent, mActionCallBack)

            ITEM_TYPE_BOOKMARKS ->
                BookmarksViewHolder.create(parent, mActionCallBack)

            ITEM_TYPE_LOADING ->
                LoadingViewHolder.create(parent)

            ITEM_TYPE_SHIMMER ->
                ShimmerViewHolder.create(parent)

            else ->
                ShimmerViewHolder.create(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieViewHolder ->
                holder.bind(mListItems[position] as Movie)
            is BookmarksViewHolder ->
                holder.bind(mListItems[position] as ListItemBookmarks)
            is ShimmerViewHolder ->
                holder.bind(mListItems[position] as ListItemShimmer)
            is LoadingViewHolder ->
                holder.bind(mListItems[position] as ListItemLoader)
        }
    }

    override fun getItemCount() = mListItems.size

    override fun getItemViewType(position: Int) = getItemType(mListItems[position])

    private fun getItemType(item: Any) =
            when (item) {
                is Movie -> ITEM_TYPE_MOVIE
                is ListItemShimmer -> ITEM_TYPE_SHIMMER
                is ListItemLoader -> ITEM_TYPE_LOADING
                is ListItemBookmarks -> ITEM_TYPE_BOOKMARKS
                else -> -1
            }

    fun updateList(mNewList: ArrayList<Any>) {
        var mDiffResult = DiffUtil.calculateDiff(ListDiffUtil(mNewList, mListItems))
        mListItems.clear()
        mListItems.addAll(mNewList)
        mDiffResult.dispatchUpdatesTo(this)
    }

    class ListDiffUtil(var mNewList: ArrayList<Any>, var mOldList: ArrayList<Any>) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            var newItem = mNewList[newItemPosition]
            var oldItem = mOldList[oldItemPosition]

            if (newItem is Movie && oldItem is Movie) {
                return newItem.mImdbID == oldItem.mImdbID
            } else if (newItem is ListItemShimmer && oldItem is ListItemShimmer) {
                return newItem.id == oldItem.id
            } else if (newItem is ListItemLoader && oldItem is ListItemLoader) {
                return true
            } else if (newItem is ListItemBookmarks && oldItem is ListItemBookmarks) {
                return true
            }
            return false
        }

        override fun getOldListSize(): Int {
            return mOldList.size
        }

        override fun getNewListSize(): Int {
            return mNewList.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            var newItem = mNewList[newItemPosition]
            var oldItem = mOldList[oldItemPosition]

            if (newItem is Movie && oldItem is Movie) {
                return newItem.isBookmarked == oldItem.isBookmarked
            } else if (newItem is ListItemShimmer && oldItem is ListItemShimmer) {
                return newItem.id == oldItem.id
            } else if (newItem is ListItemLoader && oldItem is ListItemLoader) {
                return true
            } else if (newItem is ListItemBookmarks && oldItem is ListItemBookmarks) {
                return false
            }
            return false
        }

    }
}
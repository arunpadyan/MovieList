package com.noonacademy.movielist.ui.movie_list.bookmark_list

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.noonacademy.movielist.models.Movie
import com.noonacademy.movielist.ui.movie_list.main_list.Action

class BookmarkListAdapter(private val mActionCallBack: (mMovie: Movie, mAction: Action) -> Unit)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mMovieList:ArrayList<Movie> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
      return  BookmarkedMovieViewHolder.create(parent,mActionCallBack)
    }

    override fun getItemCount(): Int {
        return mMovieList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as BookmarkedMovieViewHolder).bind(mMovieList[position])
    }

    fun updateList(mNewMovieList:ArrayList<Movie>){
        var mDiffResult = DiffUtil.calculateDiff(ListDiffUtil(mNewMovieList, mMovieList))
        mMovieList.clear()
        mMovieList.addAll(mNewMovieList)
        mDiffResult.dispatchUpdatesTo(this)
    }

    class ListDiffUtil(var mNewList: ArrayList<Movie>, var mOldList: ArrayList<Movie>) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            var newItem = mNewList[newItemPosition]
            var oldItem = mOldList[oldItemPosition]
            return newItem.mImdbID == oldItem.mImdbID
        }

        override fun getOldListSize(): Int {
            return mOldList.size
        }

        override fun getNewListSize(): Int {
            return mNewList.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
           return false
        }

    }
}
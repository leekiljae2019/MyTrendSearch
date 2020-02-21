package com.afterwork.mytrendsearch.paging

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.afterwork.mytrendsearch.R
import com.afterwork.mytrendsearch.databinding.ItemTrendBinding
import com.afterwork.mytrendsearch.model.data.TrendData
import com.afterwork.mytrendsearch.view.base.BindingViewHolder

class TrendPagingAdapter(val onClickListener: (String)-> Unit) : PagedListAdapter<TrendData, TrendPagingAdapter.MainViewHolder>(
    DIFF_CALLBACK
){

    companion object{
        val TAG = "MyPagingAdapter"

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TrendData>() {
            // The ID property identifies when items are the same.
            override fun areItemsTheSame(oldItem: TrendData, newItem: TrendData) =
                oldItem.title == newItem.title

            // If you use the "==" operator, make sure that the object implements
            // .equals(). Alternatively, write custom data comparison logic here.
            override fun areContentsTheSame(
                oldItem: TrendData, newItem: TrendData) = oldItem == newItem
        }

    }

    class MainViewHolder(view: View) : BindingViewHolder<ItemTrendBinding>(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        Log.d(TAG, "onCreateViewHolder(viewType: $viewType)")
        return MainViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_trend,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder(position: $position)")
        holder.binding.item = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.invoke(getItem(position)?.link?:"")
        }
    }
}
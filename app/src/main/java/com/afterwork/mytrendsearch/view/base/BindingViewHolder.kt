package com.afterwork.mytrendsearch.view.base

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BindingViewHolder<out T: ViewDataBinding>(_view: View): RecyclerView.ViewHolder(_view){
    val binding: T = DataBindingUtil.bind(_view)!!
}
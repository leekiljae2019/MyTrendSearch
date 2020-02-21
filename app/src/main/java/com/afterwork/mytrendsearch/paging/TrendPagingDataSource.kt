package com.afterwork.mytrendsearch.paging

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.afterwork.mytrendsearch.model.ISearchDataModel
import com.afterwork.mytrendsearch.model.data.TrendData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TrendPagingDataSource(val keyword: String, val model: ISearchDataModel, val listener: (Int)->Unit): PageKeyedDataSource<Int, TrendData>() {

    companion object {
        val TAG = "TrendPagingDataSource"
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, TrendData>
    ) {
        Log.d(TAG, "loadInitial(pageKey: 1, loadSize: ${params.requestedLoadSize})")

        if(keyword.isNullOrEmpty()){
            return
        }

        model.getNews(keyword, 1, params.requestedLoadSize)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.run {
                    Log.d(TAG, "Successed(total: $total, pageSize: ${items.size})")
                    listener.invoke(total)
                    callback.onResult(items, null, (1+items.size))
                }
            }, {
                Log.d(TAG, "Failed: ${it.localizedMessage}")
            })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, TrendData>) {
        Log.d(TAG, "loadAfter(pageKey: ${params.key}, loadSize: ${params.requestedLoadSize})")

        model.getNews(keyword, params.key, params.requestedLoadSize)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.run {
                    Log.d(TAG, "Successed(total: $total, pageSize: ${items.size})")
                    listener.invoke(total)
                    callback.onResult(items, (params.key+items.size))
                }
            }, {
                Log.d(TAG, "Failed: ${it.localizedMessage}")
            })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, TrendData>) {
        Log.d(TAG, "loadBefore(pageKey: ${params.key}, loadSize: ${params.requestedLoadSize})")
    }

    fun clear(){
        invalidate()
    }
}
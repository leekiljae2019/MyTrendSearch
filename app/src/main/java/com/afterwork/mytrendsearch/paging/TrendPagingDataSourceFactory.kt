package com.afterwork.mytrendsearch.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.afterwork.mytrendsearch.model.ISearchDataModel
import com.afterwork.mytrendsearch.model.data.TrendData

class TrendPagingDataSourceFactory (val keyword: LiveData<String>, val model: ISearchDataModel, val listener: (Int)->Unit): DataSource.Factory<Int, TrendData>() {
    val sourceLiveData = MutableLiveData<TrendPagingDataSource>()
    var dataSource: TrendPagingDataSource? = null

    override fun create(): DataSource<Int, TrendData> {
        dataSource = TrendPagingDataSource(keyword.value!!, model, listener)
        sourceLiveData.postValue(dataSource)
        return dataSource as TrendPagingDataSource
    }

    fun reset() {
        dataSource?.clear()
    }
}
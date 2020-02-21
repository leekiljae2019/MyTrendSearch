package com.afterwork.mytrendsearch.viewmodel


import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.afterwork.mytrendsearch.common.NotNullMutableLiveData
import com.afterwork.mytrendsearch.model.ISearchDataModel
import com.afterwork.mytrendsearch.model.data.TrendData
import com.afterwork.mytrendsearch.paging.TrendPagingDataSourceFactory
import com.afterwork.mytrendsearch.viewmodel.base.BaseViewModel


class MainViewModel(private val model: ISearchDataModel) : BaseViewModel(){
    companion object{
        val TAG = "MainViewModel"
    }

    val keyword : NotNullMutableLiveData<String> = NotNullMutableLiveData("")

    private val _result : NotNullMutableLiveData<String> = NotNullMutableLiveData("")
    val result : LiveData<String> get() = _result

    private val _refreshing : NotNullMutableLiveData<Boolean> = NotNullMutableLiveData(false)
    val refreshing :LiveData<Boolean> get() = _refreshing

    val pagedListBuilder: LivePagedListBuilder<Int, TrendData>
    val factory: TrendPagingDataSourceFactory

    val pageConfig = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setInitialLoadSizeHint(40)
        .setPrefetchDistance(4)
        .setPageSize(40)
        .build()

    init {
        factory = TrendPagingDataSourceFactory(keyword, model, {
            _result.value = keyword.value + "의 검색 결과: ${it}"
        })
        pagedListBuilder = LivePagedListBuilder<Int, TrendData>(factory, pageConfig)
    }

    fun onRefreshing(){
        Log.d(TAG, "onRefreshing()")
        _refreshing.value = true

        if(keyword.value.isEmpty() == false) {
            search()
        }
    }

    fun editorAction(actionId: Int): Boolean{
        Log.d(TAG, "actionSearch($actionId, ${keyword.value})")
        if(EditorInfo.IME_ACTION_SEARCH == actionId && keyword.value.isEmpty() == false){
            _refreshing.value = true
            search()
            return true
        }
        return false
    }

    fun search(){
        Log.d(TAG, "search(${keyword.value})")


        factory.reset()
    }

    fun load() = pagedListBuilder.setInitialLoadKey(1).build()

    fun loaded(){
        _refreshing.value = false
    }
}

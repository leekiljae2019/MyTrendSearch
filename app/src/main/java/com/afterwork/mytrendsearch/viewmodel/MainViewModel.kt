package com.afterwork.mytrendsearch.viewmodel


import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    val progress : NotNullMutableLiveData<Int> = NotNullMutableLiveData(0)
    val visible : NotNullMutableLiveData<Int> = NotNullMutableLiveData(View.VISIBLE)

    val trendSearchUrl: NotNullMutableLiveData<String> = NotNullMutableLiveData("")

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
//        trendSearchUrl.value = "https://trends.google.com/trends/explore?geo=KR&q=" + keyword.value

        factory.reset()
    }

    fun onTrendSearch(){
        Log.d(TAG, "onTrendSearch(${keyword.value})")
        trendSearchUrl.value = "https://trends.google.com/trends/explore?geo=KR&q=" + keyword.value
    }

    fun load() = pagedListBuilder.setInitialLoadKey(1).build()

    fun loaded(){
        _refreshing.value = false
    }
}

@BindingAdapter("webtrendinit")
fun webtrendinit(view: WebView, vm: MainViewModel){
    view.setWebViewClient(WebViewClient()) // 클릭시 새창 안뜨게
    var settings = view.getSettings() //세부 세팅 등록
    settings.setJavaScriptEnabled(true) // 웹페이지 자바스크립트 허용 여부
    settings.setSupportMultipleWindows(false) // 새창 띄우기 허용 여부
    settings.setJavaScriptCanOpenWindowsAutomatically(false) // 자바스크립트 새창 띄우기(멀티뷰) 허용 여부
    settings.setLoadWithOverviewMode(true) // 메타태그 허용 여부
    settings.setUseWideViewPort(true) // 화면 사이즈 맞추기 허용 여부
    settings.setSupportZoom(false) // 화면 줌 허용 여부
    settings.setBuiltInZoomControls(false) // 화면 확대 축소 허용 여부
    settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN) // 컨텐츠 사이즈 맞추기
    settings.setCacheMode(WebSettings.LOAD_NO_CACHE) // 브라우저 캐시 허용 여부
    settings.setDomStorageEnabled(true) // 로컬저장소 허용 여부

    view.webChromeClient = object : WebChromeClient(){
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            Log.d("BindingAdapter", "onProgressChanged($newProgress)")
            vm.progress.value = newProgress
            if(newProgress == 100){
                vm.visible.value = View.GONE
            }
        }
    }

}

//@BindingAdapter("webViewUrl")
//fun updateUrl(view: WebView, url: String?) {
//    url?.let {
//        view.loadUrl(url)
//    }
//}
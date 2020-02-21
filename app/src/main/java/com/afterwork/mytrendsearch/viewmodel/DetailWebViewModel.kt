package com.afterwork.mytrendsearch.viewmodel

import android.net.http.SslError
import android.util.Log
import android.view.View
import android.webkit.*
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.afterwork.mytrendsearch.common.NotNullMutableLiveData
import com.afterwork.mytrendsearch.viewmodel.base.BaseViewModel

class DetailWebViewModel(val linkUrl: String) : BaseViewModel(){
    companion object{
        val TAG = "DetailWebViewModel"
    }

    val progress : NotNullMutableLiveData<Int> = NotNullMutableLiveData(0)
    val visible : NotNullMutableLiveData<Int> = NotNullMutableLiveData(View.VISIBLE)

    val url: LiveData<String> by lazy { MutableLiveData(linkUrl) }

}

@BindingAdapter("webinit")
fun webInit(view: WebView, vm: DetailWebViewModel){
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
    view.loadUrl(vm.url.value)

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
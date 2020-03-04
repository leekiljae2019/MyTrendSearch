package com.afterwork.mytrendsearch.common

import com.afterwork.mytrendsearch.model.ISearchApi
import com.afterwork.mytrendsearch.model.ISearchDataModel
import com.afterwork.mytrendsearch.model.SearchDataModelImpl
import com.afterwork.mytrendsearch.viewmodel.DetailWebViewModel
import com.afterwork.mytrendsearch.viewmodel.MainViewModel
import com.afterwork.mytrendsearch.viewmodel.TrendWebViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val API_BASE_URL = "https://openapi.naver.com/v1/search/"


var modelPart = module {
    factory<ISearchDataModel> {
        SearchDataModelImpl(get())
    }
}

var viewModelPart = module {
    viewModel {
        MainViewModel(get())
    }

    viewModel {(linkUrl: String) ->
        DetailWebViewModel(linkUrl)
    }

    viewModel {(linkUrl: String) ->
        TrendWebViewModel(linkUrl)
    }
}


var retrofitPart = module {
    single<ISearchApi>{
        Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ISearchApi::class.java)
    }
}

var myDiModule = listOf(
    modelPart,
    viewModelPart,
    retrofitPart
)
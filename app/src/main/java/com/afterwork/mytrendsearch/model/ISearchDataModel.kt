package com.afterwork.mytrendsearch.model

import com.afterwork.mytrendsearch.model.data.SearchResult
import io.reactivex.Single


interface ISearchDataModel {
    fun getNews(query: String, start: Int, display: Int): Single<SearchResult>
}
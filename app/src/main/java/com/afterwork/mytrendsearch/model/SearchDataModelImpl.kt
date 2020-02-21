package com.afterwork.mytrendsearch.model

import com.afterwork.mytrendsearch.model.data.SearchResult
import io.reactivex.Single

class SearchDataModelImpl(private val ISearchApi: ISearchApi) : ISearchDataModel{
    override fun getNews(query: String, start: Int, display: Int): Single<SearchResult> {
        return ISearchApi.getNews(query, start, display)
    }
}
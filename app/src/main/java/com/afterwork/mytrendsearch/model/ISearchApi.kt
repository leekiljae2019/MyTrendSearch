package com.afterwork.mytrendsearch.model

import com.afterwork.mytrendsearch.model.data.SearchResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ISearchApi {
    @Headers("X-Naver-Client-Id: _oeiBHtPZ3FELyRDHqy_",
            "X-Naver-Client-Secret: GhkBTMjpxv")
    @GET("news.json")
    fun getNews(@Query("query") query: String,
                @Query("start") start: Int,
                @Query("display") display: Int): Single<SearchResult>

}
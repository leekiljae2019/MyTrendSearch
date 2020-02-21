package com.afterwork.mytrendsearch.model.data

data class SearchResult(val total: Int,
                        val start: Int,
                        val display: Int,
                        val items: List<TrendData>)
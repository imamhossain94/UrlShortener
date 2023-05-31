package com.newagedevs.url_shortener.network.chilpit

import com.newagedevs.url_shortener.network.clck.ru.ClckRuService
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.DataRetainPolicy
import com.skydoves.sandwich.ResponseDataSource
import kotlinx.coroutines.CoroutineScope

class ChilpItClient(private val service: ClckRuService) {

    fun short(
        url: String,
        dataSource: ResponseDataSource<String>,
        coroutineScope: CoroutineScope,
        onResult: suspend (response: ApiResponse<String>) -> Unit
    ) {
        dataSource
            .dataRetainPolicy(DataRetainPolicy.NO_RETAIN)
            .retry(3, 5000L)
            .suspendCombine(service.short(url), coroutineScope, onResult)
            .request()
    }

}
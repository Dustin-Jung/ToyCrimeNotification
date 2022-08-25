package com.android.aop.part2.toycrimenotification.data.source.remote

import com.android.aop.part2.toycrimenotification.api.response.KakaoSearchResponse
import com.android.aop.part2.toycrimenotification.util.Result

interface KakaoRemoteDataSource {

    suspend fun getSearchList(location: String): Result<KakaoSearchResponse>
}
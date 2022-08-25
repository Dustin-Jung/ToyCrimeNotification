package com.android.aop.part2.toycrimenotification.api

import com.android.aop.part2.toycrimenotification.api.response.KakaoSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface KakaoApi {

    companion object{
        private const val REST_API_KEY = "db1aa509581919ff0fc66b4246e17549"
        private const val SEARCH = "/v2/local/search/address.json"
    }

    @Headers("Authorization: KakaoAK $REST_API_KEY")
    @GET(SEARCH)
    fun search(
        @Query("query") query: String,
        @Query("analyze_type") analyze_type: String = "exact",
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 10
    ): Call<KakaoSearchResponse>
}
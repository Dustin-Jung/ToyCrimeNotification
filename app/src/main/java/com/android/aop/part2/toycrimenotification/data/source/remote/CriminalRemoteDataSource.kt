package com.android.aop.part2.toycrimenotification.data.source.remote

import com.android.aop.part2.toycrimenotification.api.response.CriminalResponse
import com.android.aop.part2.toycrimenotification.util.Result

interface CriminalRemoteDataSource {

    suspend fun getRemoteCriminals(): Result<List<CriminalResponse>>
}
package com.android.aop.part2.toycrimenotification.data.repo

import com.android.aop.part2.toycrimenotification.api.response.CriminalResponse
import com.android.aop.part2.toycrimenotification.data.source.local.CriminalLocalDataSource
import com.android.aop.part2.toycrimenotification.data.source.remote.CriminalRemoteDataSource
import com.android.aop.part2.toycrimenotification.room.entity.CriminalEntity
import com.android.aop.part2.toycrimenotification.util.Result
import javax.inject.Inject

class CriminalRepositoryImpl @Inject constructor(
    private val criminalRemoteDataSource: CriminalRemoteDataSource,
    private val criminalLocalDataSource: CriminalLocalDataSource
) : CriminalRepository {

    override suspend fun getRemoteCriminals(): Result<List<CriminalResponse>> =
        criminalRemoteDataSource.getRemoteCriminals()

    override suspend fun getLocalCriminals(): Result<List<CriminalEntity>> =
        criminalLocalDataSource.getLocalCriminals()

    override suspend fun registerCriminalEntityList(toZipList: List<CriminalEntity>): Boolean =
        criminalLocalDataSource.registerCriminalEntityList(toZipList)

    override suspend fun getCriminalEntity(name: String): Result<CriminalEntity> =
        criminalLocalDataSource.getCriminalEntity(name)


}
package com.android.aop.part2.toycrimenotification.data.source.local

import com.android.aop.part2.toycrimenotification.room.entity.CriminalEntity
import com.android.aop.part2.toycrimenotification.util.Result

interface CriminalLocalDataSource {

    suspend fun getLocalCriminals(): Result<List<CriminalEntity>>

    suspend fun registerCriminalEntityList(toZipList: List<CriminalEntity>): Boolean

    suspend fun getCriminalEntity(name: String): Result<CriminalEntity>

}
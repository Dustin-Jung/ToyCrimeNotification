package com.android.aop.part2.toycrimenotification.data.source.local

import com.android.aop.part2.toycrimenotification.room.dao.CriminalDao
import com.android.aop.part2.toycrimenotification.room.entity.CriminalEntity
import com.android.aop.part2.toycrimenotification.util.Result
import javax.inject.Inject


class CriminalLocalDataSourceImpl @Inject constructor(private val criminalDao: CriminalDao) :
    CriminalLocalDataSource {

    override suspend fun getLocalCriminals(): Result<List<CriminalEntity>> {
        return try {
            Result.Success(criminalDao.getAll())
        } catch (e: Exception){
            Result.Error(e)
        }
    }

    override suspend fun registerCriminalEntityList(toZipList: List<CriminalEntity>): Boolean {
        return try {
            registerAll(toZipList)
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getCriminalEntity(name: String): Result<CriminalEntity> {
        return try {
            Result.Success(criminalDao.getCriminalEntity(name))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    private fun registerAll(list: List<CriminalEntity>): Boolean {
        var isAllSave = true
        list.forEach {
            isAllSave = isAllSave.and(criminalDao.registerCriminalEntity(it) > 0)
        }
        return isAllSave
    }
}
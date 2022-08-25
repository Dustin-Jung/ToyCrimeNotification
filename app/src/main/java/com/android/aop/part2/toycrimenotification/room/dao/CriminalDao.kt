package com.android.aop.part2.toycrimenotification.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.aop.part2.toycrimenotification.room.entity.CriminalEntity

@Dao
interface CriminalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun registerCriminalEntity(criminalList: CriminalEntity): Long

    @Query("SELECT * FROM criminal_table")
    fun getAll(): List<CriminalEntity>

    @Query("SELECT * FROM criminal_table WHERE `name` = (:name) ")
    fun getCriminalEntity(name: String): CriminalEntity
}
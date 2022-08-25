package com.android.aop.part2.toycrimenotification.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.aop.part2.toycrimenotification.room.dao.CriminalDao
import com.android.aop.part2.toycrimenotification.room.entity.CriminalEntity

@Database(entities = [CriminalEntity::class], version = 1)
abstract class CriminalDatabase : RoomDatabase(){
    abstract fun criminalDao(): CriminalDao
}
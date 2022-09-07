package com.android.aop.part2.toycrimenotification.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.aop.part2.toycrimenotification.data.model.CriminalItem

@Entity(tableName = "criminal_table")
data class CriminalEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "longitude") val longitude: Double,
    @ColumnInfo(name = "latitude") val latitude: Double,
) {

    fun toCriminalItem(): CriminalItem =
        CriminalItem(
            name = name,
            addressReal = address,
            longitude = longitude,
            latitude = latitude
        )
}

package com.hk.baseproject.data.source.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 *
 * @param countryName       name of country
 * @param capital capital name
 * @param id          id of the task
 */
@Entity(tableName = "country")
data class CountryT @JvmOverloads constructor(
    @ColumnInfo(name = "name") var name: String? = "",
    @ColumnInfo(name = "capital") var capital: String? = "",
    @ColumnInfo(name = "flag_url") var flagUrl: String? = "",
    @PrimaryKey @ColumnInfo(name = "id") var id: Int = 0
) {

}

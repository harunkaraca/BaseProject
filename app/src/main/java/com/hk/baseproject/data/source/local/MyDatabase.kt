package com.hk.baseproject.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CountryT::class], version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {

    abstract fun countryDao(): CountryDao
}
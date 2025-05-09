package com.example.initiations

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.initiations.daos.CommonDao
import com.example.initiations.di.entities.InitiationFiled


@Database(
    entities = [
        InitiationFiled::class
    ],
    version = 4,
    exportSchema = false,

)
abstract class AppDatabase:RoomDatabase() {
    abstract fun commonDao(): CommonDao
}
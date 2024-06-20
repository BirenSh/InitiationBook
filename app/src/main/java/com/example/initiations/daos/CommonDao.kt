package com.example.initiations.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.initiations.di.entities.InitiationFiled

@Dao
interface CommonDao {
    @Query("select * from initiationperson")
    suspend fun getAllInitiationPersons(): List<InitiationFiled>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInitiationDetail(initiationFiled: InitiationFiled):Long

}
package com.example.initiations.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.initiations.di.entities.InitiationFiled

@Dao
interface CommonDao {
    @Query("select * from initiationperson where initiationDate  LIKE '%' || :currentYear || '%' ")
    suspend fun getAllInitiationPersons(currentYear:Int): List<InitiationFiled>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInitiationDetail(initiationFiled: InitiationFiled):Long

    @RawQuery
    suspend fun getFilterMember(rowValue:SimpleSQLiteQuery):List<InitiationFiled>

}
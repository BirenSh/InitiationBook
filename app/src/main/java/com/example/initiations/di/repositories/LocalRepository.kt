package com.example.initiations.di.repositories

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.initiations.daos.CommonDao
import com.example.initiations.di.entities.InitiationFiled
import javax.inject.Inject

class LocalRepository @Inject constructor(val commonDao: CommonDao) {
    suspend fun insertInitiationDetail(initiationFiled: InitiationFiled):Long{
        return commonDao.insertInitiationDetail(initiationFiled)
    }
    suspend fun getInitiationMembers() = commonDao.getAllInitiationPersons()
    suspend fun getFilterMembers(rowQuery: SimpleSQLiteQuery):List<InitiationFiled>{
         return commonDao.getFilterMember(rowQuery)
    }
}
package com.example.initiations.di.repositories

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.initiations.daos.CommonDao
import com.example.initiations.di.entities.InitiationFiled
import java.util.Calendar
import javax.inject.Inject

class LocalRepository @Inject constructor(val commonDao: CommonDao) {
    suspend fun insertListOfInitiationDetail(initiationFiledList:List<InitiationFiled> ) {
        return commonDao.insertListOfMembers(initiationFiledList)
    }

    suspend fun insertInitiationDetail(initiationFiled: InitiationFiled): Long {
        return commonDao.insertInitiationDetail(initiationFiled)
    }

    suspend fun getInitiationMembers(): List<InitiationFiled> {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        return commonDao.getAllInitiationPersons(currentYear)
    }

    suspend fun getFilterMembers(rowQuery: SimpleSQLiteQuery): List<InitiationFiled> {
        return commonDao.getFilterMember(rowQuery)
    }
    suspend fun deleteAllMembers(){
        return commonDao.deleteAllMembers()
    }
}
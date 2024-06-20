package com.example.initiations.di.repositories

import com.example.initiations.daos.CommonDao
import com.example.initiations.di.entities.InitiationFiled
import javax.inject.Inject

class MainRepository @Inject constructor(val commonDao: CommonDao) {

    fun getInitiationFiled(): InitiationFiled {
        return InitiationFiled(
            10,
            personName = "Birendra Sharma"
        )
    }
}
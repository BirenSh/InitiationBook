package com.example.initiations.di.repositories

import com.example.initiations.daos.CommonDao
import javax.inject.Inject

class MainRepository @Inject constructor(val commonDao: CommonDao) {


}
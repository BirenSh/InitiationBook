package com.example.initiations.di.modules

//import com.example.initiations.AppDatabase.Companion.ALL_MIGRATION
import android.content.Context
import androidx.room.Room
import com.example.initiations.AppDatabase
import com.example.initiations.daos.CommonDao
import com.example.initiations.di.repositories.LocalRepository
import com.example.initiations.di.repositories.MainRepository
import com.example.initiations.util.Migration_1_2
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase = Room.databaseBuilder(
        appContext,
        AppDatabase::class.java,
        "room_database"
    ).addMigrations(Migration_1_2())
        .build()

    @Provides
    @Singleton
    fun provideCommonDao(appDatabase: AppDatabase):CommonDao{
        return appDatabase.commonDao()
    }

    @Provides
    @Singleton
    fun provideCommonDaoRepository(commonDao: CommonDao): LocalRepository {
        return LocalRepository(commonDao)
    }

    @Provides
    @Singleton
    fun provideCommonDaoMainRepository(commonDao: CommonDao): MainRepository{
        return MainRepository(commonDao)
    }



}
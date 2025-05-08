package com.example.initiations.di.repositories
import android.content.Context
import com.example.initiations.di.modules.GoogleSheetHelper
import com.example.initiations.di.modules.SheetAppendResult
import com.example.initiations.di.repositories.LocalRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    suspend fun createRow(values: List<Any>): SheetAppendResult = withContext(Dispatchers.IO) {
        GoogleSheetHelper.createRow(context, values)
    }

    suspend fun readSheet(range: String = "Sheet1!A:D"): List<List<Any>> = withContext(Dispatchers.IO) {
        GoogleSheetHelper.readSheet(context, range)
    }

    suspend fun updateRow(index: Int, values: List<Any>) = withContext(Dispatchers.IO) {
        GoogleSheetHelper.updateRow(context, index, values)
    }


}

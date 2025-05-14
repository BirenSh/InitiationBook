package com.example.initiations.di.viewmodols

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.initiations.di.entities.InitiationFiled
import com.example.initiations.di.repositories.LocalRepository
import com.example.initiations.di.repositories.RemoteDataRepository
import com.example.initiations.util.UiState
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirstTimeSyncViewModel @Inject constructor(
    private val localRepository: LocalRepository,
    private val firestore: FirebaseFirestore,
    private val remoteDataRepository: RemoteDataRepository
):ViewModel() {
    init {
        getSheetData()
    }

    private fun getSheetData(){
        viewModelScope.launch {

            try {
                val sheetData = remoteDataRepository.readAllSheetData()
                if (sheetData.success && !sheetData.data.isNullOrEmpty()){
                    localRepository.upsertMembers(sheetData.data)
                }
            }catch (e:Exception){
                println("===sheet: ${e.message}")
            }

        }
    }
}
package com.example.initiations.di.viewmodols

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.initiations.di.entities.InitiationFiled
import com.example.initiations.di.repositories.LocalRepository
import com.example.initiations.di.repositories.RemoteDataRepository
import com.example.initiations.util.UiState
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import firebase.com.protolitewrapper.BuildConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoogleSheetsViewModel @Inject constructor (
    private val remoteDataRepository: RemoteDataRepository
):ViewModel() {
    private val _initiationState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val initiationState: StateFlow<UiState<Unit>> = _initiationState

    fun createRow(initiationDetails: InitiationFiled) {
        viewModelScope.launch {
            _initiationState.value = UiState.Loading
            // Convert InitiationFiled to a row (List<Any>)
            val initiationDetail = listOf(
                initiationDetails.personName,
                initiationDetails.personAge,
                initiationDetails.gender,
                initiationDetails.education,
                initiationDetails.fullAddress,
                initiationDetails.masterName,
                initiationDetails.introducerName,
                initiationDetails.guarantorName,
                initiationDetails.templeName,
                initiationDetails.initiationDate,
                initiationDetails.meritFee,
                initiationDetails.is2DaysDharmaClassAttend.toString(),
                initiationDetails.dharmaMeetingDate
            )
            val sheetResponse = remoteDataRepository.createRow(initiationDetail)
            if (sheetResponse.success) {
                _initiationState.value = UiState.Success()
            }else{
                _initiationState.value = UiState.Error(sheetResponse.errorMessage)
            }

        }
        BuildConfig.BUILD_TYPE
    }

    fun updateRow(index: Int, values: List<Any>) {
        viewModelScope.launch {
            remoteDataRepository.updateRow(index, values)
        }
    }



}
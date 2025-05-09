package com.example.initiations.di.viewmodols

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.initiations.di.entities.InitiationFiled
import com.example.initiations.di.repositories.RemoteDataRepository
import com.example.initiations.util.UiState
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

    private val _memberState = MutableStateFlow<UiState<List<InitiationFiled>>>(UiState.Idle)
    val memberState: StateFlow<UiState<List<InitiationFiled>>> = _memberState



    fun formInputValidation(state: InitiationFiled):Boolean{
        return state.personName.isNotBlank() &&
                state.personAge.isNotBlank() &&
                state.gender.isNotBlank() &&
                state.education.isNotBlank() &&
                state.fullAddress.isNotBlank() &&
                state.masterName.isNotBlank() &&
                state.introducerName.isNotBlank() &&
                state.guarantorName.isNotBlank() &&
                state.templeName.isNotBlank() &&
                state.contact.isNotBlank()
    }

    fun createRow(initiationDetails: InitiationFiled) {
        viewModelScope.launch {
            // Convert InitiationFiled to a row (List<Any>)
            val initiationDetailMap = mapOf(
                "Person ID" to initiationDetails.personId,
                "Name" to initiationDetails.personName,
                "Age" to initiationDetails.personAge,
                "Gender" to initiationDetails.gender,
                "Education" to initiationDetails.education,
                "Address" to initiationDetails.fullAddress,
                "Master" to initiationDetails.masterName,
                "Introducer" to initiationDetails.introducerName,
                "Guarantor" to initiationDetails.guarantorName,
                "Temple" to initiationDetails.templeName,
                "Initiation Date" to initiationDetails.initiationDate,
                "Merit Fee" to initiationDetails.meritFee,
                "2-Day Dharma Class" to initiationDetails.is2DaysDharmaClassAttend.toString(),
                "Dharma Meeting Date" to initiationDetails.dharmaMeetingDate
            )

            val sheetResponse = remoteDataRepository.createRow(initiationDetailMap)
            if (sheetResponse.success) {
                _initiationState.value = UiState.Success()
            }else{
                _initiationState.value = UiState.Error(sheetResponse.errorMessage)
            }

        }
        BuildConfig.BUILD_TYPE
    }

    fun getSheetData(){
        viewModelScope.launch {
            _initiationState.value = UiState.Loading
            val sheetData = remoteDataRepository.readAllSheetData()
            if (sheetData.success){
                _memberState.value = UiState.Success(sheetData.data)
                println("===sheet: ${sheetData.data}")
            }else{
                _memberState.value = UiState.Error(sheetData.errorMessage)
                println("===sheet: ${sheetData.errorMessage}")
            }
        }
    }

    fun updateRow(index: Int, values: List<Any>) {
        viewModelScope.launch {
            remoteDataRepository.updateRow(index, values)
        }
    }



}
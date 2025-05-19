package com.example.initiations.di.viewmodols

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.initiations.di.entities.InitiationFiled
import com.example.initiations.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(): ViewModel() {
//    private val _scanResult = MutableStateFlow<InitiationFiled?>(null)
//    val scanResult: StateFlow<InitiationFiled?> = _scanResult

     val _scanResult = MutableStateFlow<UiState<InitiationFiled?>>(UiState.Idle)
    val scanResult: StateFlow<UiState<InitiationFiled?>> = _scanResult


    fun setResult(data: InitiationFiled?) {
        viewModelScope.launch {
            _scanResult.value = UiState.Loading
            delay(3000)
            _scanResult.value = UiState.Success(data)

        }

    }

    fun resetState() {
        _scanResult.value = UiState.Idle
    }
}
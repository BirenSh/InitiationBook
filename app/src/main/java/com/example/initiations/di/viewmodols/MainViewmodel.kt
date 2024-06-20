package com.example.initiations.di.viewmodols


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.initiations.di.entities.InitiationFiled
import com.example.initiations.di.repositories.LocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewmodel @Inject constructor (
    private val localRepository: LocalRepository
): ViewModel() {
    private val _tasks = mutableStateOf<Long?>(null)
    val tasks: State<Long?> = _tasks
    private val _initiationMembers = mutableStateOf(listOf<InitiationFiled>())
    val initiationMembers:State<List<InitiationFiled>> = _initiationMembers

    init {
        getAllInitiationMembers()
    }

    fun insertInitiationDetails(initiationDetails:InitiationFiled){
        viewModelScope.launch {
            val insertingData = localRepository.insertInitiationDetail(initiationDetails)
            if (insertingData == null){
                _tasks.value = 0L
            }else _tasks.value = insertingData
        }
    }
    fun getAllInitiationMembers(){
        viewModelScope.launch {
            val getMember = localRepository.getInitiationMembers()
            viewModelScope.launch {
                delay(3000)
                _initiationMembers.value = getMember
            }
        }
    }
    fun resetvalue(){
        _tasks.value = null
    }
}
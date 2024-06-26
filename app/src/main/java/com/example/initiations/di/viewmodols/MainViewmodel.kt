package com.example.initiations.di.viewmodols


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.initiations.di.entities.InitiationFiled
import com.example.initiations.di.repositories.LocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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
            _initiationMembers.value = getMember
        }
    }
    fun getFilterMembers(gender:String?, selectedYear:Int?, dharmaMeeting:Boolean){
        viewModelScope.launch {
            val initialQuery =StringBuilder( "Select * from initiationPerson where initiationDate like '%$selectedYear%' ")
            if (!gender.isNullOrEmpty()){
                 initialQuery.append ("and gender = '$gender'")
            }
            if (dharmaMeeting){
                initialQuery.append ("and is2DaysDharmaClassAttend = '$dharmaMeeting'")
            }
            val rowQry = SimpleSQLiteQuery(initialQuery.toString())
            println("=======final: $initialQuery")
            val filterItems =  localRepository.getFilterMembers(rowQry)
            _initiationMembers.value = filterItems
        }
    }
}
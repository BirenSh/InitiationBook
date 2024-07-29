package com.example.initiations.di.viewmodols


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.initiations.di.entities.InitiationFiled
import com.example.initiations.di.repositories.LocalRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewmodel @Inject constructor (
    private val localRepository: LocalRepository,
    private val firestore: FirebaseFirestore,
): ViewModel() {
    private val _initiationMembers = MutableStateFlow(listOf<InitiationFiled>())
    val initiationMembers = _initiationMembers

    private val _searchedList= MutableStateFlow(listOf<InitiationFiled>())
    val searchedList = _searchedList

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading
    init {
        getAllInitiationMembers()
    }
//




    fun insertInitiationDetails(initiationDetails:InitiationFiled){
        viewModelScope.launch {
            localRepository.insertInitiationDetail(initiationDetails)
        }
    }
    fun getAllInitiationMembers(){
        viewModelScope.launch {
            _isLoading.value = true
            delay(5000)
            val getMember = localRepository.getInitiationMembers()
            println("=========second: ${getMember.size}")
            _initiationMembers.value = getMember    //updating value to initialize the list for the first time
            _searchedList.value = getMember         // updating value to to display all the list
            _isLoading.value = false

        }
    }

    fun getFilterMembers(gender:String?, selectedYear:Int?, dharmaMeeting:Boolean){
        viewModelScope.launch {
            val initialQuery =StringBuilder( "Select * from initiationPerson where initiationDate like '%$selectedYear%' ")
            if (!gender.isNullOrEmpty()){
                 initialQuery.append ("and gender = '$gender'")
            }
            if (dharmaMeeting){
                initialQuery.append ("and is2DaysDharmaClassAttend = '1'")
            }
            val rowQry = SimpleSQLiteQuery(initialQuery.toString())
            println("=======final: $initialQuery")
            val filterItems =  localRepository.getFilterMembers(rowQry)
            _initiationMembers.value = filterItems
            _searchedList.value = filterItems
        }
    }

    fun onSearchTextChange(searchTxt:String){
        if (searchTxt.isEmpty()){
            _searchedList.value = _initiationMembers.value
        }else{
            val filterChar = _initiationMembers.value.filter { it.personName.contains(searchTxt,true) }
            _searchedList.value = filterChar
        }
    }
}
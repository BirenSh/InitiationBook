package com.example.initiations.di.viewmodols

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.initiations.di.entities.InitiationFiled
import com.example.initiations.di.repositories.LocalRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InitiationInputDataViewModel @Inject constructor (
    private val localRepository: LocalRepository,
    private val firestore: FirebaseFirestore,
):ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading

    private val _memberCreatedResponse = MutableStateFlow(false)
    val memberCreatedResponse = _memberCreatedResponse

    fun insertInitiationDetails(initiationDetails: InitiationFiled){
        viewModelScope.launch {
            localRepository.insertInitiationDetail(initiationDetails)
        }
    }

    fun saveMemberToFirebase(initiationDetails: InitiationFiled){
        _isLoading.value = true
        _memberCreatedResponse.value = false
        viewModelScope.launch {
            val saveDataRef = firestore.collection("Hong ci").document("2024").collection("InitiationMembers")
            saveDataRef.add(initiationDetails)
                .addOnSuccessListener {
                    println("====member added")

                    _isLoading.value = false
                    _memberCreatedResponse.value = true

                    viewModelScope.launch {
                        insertInitiationDetails(initiationDetails)
                    }
                }
                .addOnFailureListener {
                    println("====member added: $it")
                    _memberCreatedResponse.value = false
                    _isLoading.value = false
                }
        }
    }
}
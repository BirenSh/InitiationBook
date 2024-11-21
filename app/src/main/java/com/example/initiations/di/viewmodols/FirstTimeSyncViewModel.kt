package com.example.initiations.di.viewmodols

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.initiations.di.entities.InitiationFiled
import com.example.initiations.di.repositories.LocalRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirstTimeSyncViewModel @Inject constructor(
    private val localRepository: LocalRepository,
    private val firestore: FirebaseFirestore
):ViewModel() {

    fun getMembersFromFirebase() {
        // getting data from firebase
        viewModelScope.launch {
            val memberList = mutableListOf<InitiationFiled>()
            val firebaseRef =
                firestore.collection("Hong ci").document("2024").collection("InitiationMembers")
            firebaseRef.get()
                .addOnSuccessListener { documentSnapshot ->
                    for (doc in documentSnapshot) {
                        val data = doc.toObject<InitiationFiled>(InitiationFiled::class.java)
                        memberList.add(data)
                    }

                    viewModelScope.launch {
                        if (memberList.isNotEmpty()){
                            localRepository.deleteAllMembers()
                            localRepository.insertListOfInitiationDetail(memberList)
                        }else{
                            println("==========from firebase is emptyu")

                        }
                    }

                }
                .addOnFailureListener { exception ->
                    println("========ex: $exception")
                }


        }
    }


}
package com.example.initiations.di.repositories

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RemoteDataRepository @Inject constructor(
    val firestore: FirebaseFirestore,
    private val localRepository: LocalRepository
) :ViewModel() {

}
package com.example.initiations.di.viewmodols


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.initiations.di.entities.InitiationFiled
import com.example.initiations.di.repositories.LocalRepository
import com.example.initiations.di.repositories.RemoteDataRepository
import com.example.initiations.util.UiState
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewmodel @Inject constructor (
    private val localRepository: LocalRepository,
    private val firestore: FirebaseFirestore,
    private val remoteDataRepository: RemoteDataRepository
): ViewModel() {

    private val _memberListState = MutableStateFlow<UiState<List<InitiationFiled>>>(UiState.Idle)
    val memberListState: StateFlow<UiState<List<InitiationFiled>>> = _memberListState


    fun getAllMemberList() {
        viewModelScope.launch {
            _memberListState.value = UiState.Loading
            try {
                val members = localRepository.getInitiationMembers()
                _memberListState.value = UiState.Success(members)
            } catch (e: Exception) {
                _memberListState.value = UiState.Error(e.message ?: "Unexpected error")
            }
        }
    }
}
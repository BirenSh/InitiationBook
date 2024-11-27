package com.example.initiations.di.viewmodols

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.initiations.util.AppConstant
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val firebaseAuth: FirebaseAuth
):ViewModel() {
    private val _loginResult = MutableSharedFlow<String>()
    val loginResult = _loginResult
    private val _loginLoading = MutableStateFlow<Boolean>(false)
    val loginLoading = _loginLoading

    fun validateUserLogin(){

    }

    fun loginFromFirebaseAuth(email:String,password:String){
        viewModelScope.launch {
            try {
                firebaseAuth.signInWithEmailAndPassword(
                    email,password
                )
                    .addOnCompleteListener {result->
                        viewModelScope.launch {
                            _loginLoading.emit(true)
                            if ( result.isSuccessful){
                                _loginResult.emit( AppConstant.ValueState.SUCCESS)
                                println("============sucess")
                            }else{
                                _loginLoading.emit(false)
                                _loginResult.emit(AppConstant.ValueState.FAILED)
                            }
                        }
                    }
            }catch (e:Exception){
                _loginLoading.emit(false)
                _loginResult.emit( AppConstant.ValueState.FAILED)
            }

        }
    }
}
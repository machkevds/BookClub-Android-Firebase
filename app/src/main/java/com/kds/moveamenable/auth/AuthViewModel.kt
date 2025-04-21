package com.kds.moveamenable.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _isSignedIn = MutableStateFlow(auth.currentUser != null)
    val isSignedIn: StateFlow<Boolean> = _isSignedIn

    init {
        auth.addAuthStateListener {
            _isSignedIn.value = it.currentUser != null
        }
    }

    fun signOut() {
        viewModelScope.launch {
            auth.signOut()
        }
    }
}

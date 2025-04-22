package com.kds.bookclub.auth

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel : ViewModel() {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val _isSignedIn = MutableStateFlow(firebaseAuth.currentUser != null)
    val isSignedIn: StateFlow<Boolean> = _isSignedIn

    init {
        firebaseAuth.addAuthStateListener {
            _isSignedIn.value = it.currentUser != null
        }
    }
}

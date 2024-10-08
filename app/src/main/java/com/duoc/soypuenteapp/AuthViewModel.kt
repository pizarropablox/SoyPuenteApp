package com.duoc.soypuenteapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth


class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> get() = _authState

    fun signup(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState(isSuccess = true)
                } else {
                    _authState.value = AuthState(isError = true, errorMessage = task.exception?.message)
                }
            }
    }
    fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState(isSuccess = true)
                } else {
                    _authState.value = AuthState(isError = true, errorMessage = task.exception?.message)
                }
            }
    }

    fun validarFormularioRegistro(
        email: String,
        password: String,
        username: String
    ): Boolean {
        return when {
            email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> false
            password.length < 6 -> false
            username.isBlank() -> false
            else -> true
        }
    }

}

data class AuthState(
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)

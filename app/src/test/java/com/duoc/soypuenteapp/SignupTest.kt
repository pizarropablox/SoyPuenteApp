package com.duoc.soypuenteapp

import org.junit.Test
import org.junit.Assert.*
import java.util.regex.Pattern

class SignupTest {

    @Test
    fun testEmailFormatIsValid() {
        val email = "test@gmail.com"
        assertTrue(isValidEmail(email))
    }

    @Test
    fun testEmailFormatIsInvalid() {
        val email = "invalid-email"
        assertFalse(isValidEmail(email))
    }

    // Función personalizada para verificar si el formato de un correo es válido
    private fun isValidEmail(email: String): Boolean {
        val emailPattern = Pattern.compile(
            "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        )
        return emailPattern.matcher(email).matches()
    }
}

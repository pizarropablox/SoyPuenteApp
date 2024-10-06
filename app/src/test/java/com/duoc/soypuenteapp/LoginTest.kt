package com.duoc.soypuenteapp

import org.junit.Assert.assertEquals
import org.junit.Test

class LoginTest {

    @Test
    fun testValidUserLogin() {
        val email = "test@example.com"
        val password = "123456"

        // Simular el login y verificar el resultado
        val result = validarUsuario(email, password)
        assertEquals(true, result)
    }

    // Función simulada para validar usuario
    fun validarUsuario(email: String, password: String): Boolean {
        return email == "test@example.com" && password == "123456"
    }

    @Test
    fun testEmptyFields() {
        val email = ""
        val password = ""

        val result = validarUsuario(email, password)
        assertEquals(false, result)
    }

}


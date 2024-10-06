package com.duoc.soypuenteapp

import org.junit.Assert.assertEquals
import org.junit.Test

class AdminPageTest {

    @Test
    fun testAdminUpdateUser() {
        val nombre = "Juan"
        val direccion = "Calle Falsa 123"
        val resultadoEsperado = true

        // Simular la actualización de un usuario y verificar el resultado
        val result = actualizarUsuario(nombre, direccion)
        assertEquals(resultadoEsperado, result)
    }

    // Función simulada para actualizar un usuario
    fun actualizarUsuario(nombre: String, direccion: String): Boolean {
        return nombre.isNotEmpty() && direccion.isNotEmpty()
    }
}

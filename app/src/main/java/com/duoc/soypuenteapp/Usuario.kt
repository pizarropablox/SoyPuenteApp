package com.duoc.soypuenteapp

data class Usuario(
    val uid: String = "", // UID proporcionado por Firebase Authentication
    val email: String = "",
    val nombre: String = "",
    val direccion: String = ""
)

package com.duoc.soypuenteapp.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.duoc.soypuenteapp.AuthViewModel
import com.duoc.soypuenteapp.R
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.material3.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.duoc.soypuenteapp.Usuario


@Composable
fun SignupPage(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel){

    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var confirmPassword by remember {
        mutableStateOf("")
    }
    var nombre by remember {
        mutableStateOf("")
    }
    var direccion by remember {
        mutableStateOf("")
    }


    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Registrate Aquí", fontSize = 25.sp)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = {
                Text(text = "Correo")
            }
        )
        //CONTRASEÑA
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = {
                Text(text = "Contraseña")
            },
            textStyle = TextStyle(fontSize = 24.sp),
            visualTransformation = PasswordVisualTransformation()
        )
        // CONFIRMAR CONTRASEÑA
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = {
                Text(text = "Confirmar Contraseña")
            },
            textStyle = TextStyle(fontSize = 24.sp),
            visualTransformation = PasswordVisualTransformation()
        )

        //NOMBRE
        OutlinedTextField(
            value = nombre,
            onValueChange = {
                nombre = it
            },
            label = {
                Text(text = "Nombre")
            }
        )

        // DIRECCION
        OutlinedTextField(
            value = direccion,
            onValueChange = {
                direccion = it
            },
            label = {
                Text(text = "Dirección")
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (password == confirmPassword) {
                // Crear usuario con Firebase Authentication
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Obtener el usuario recién registrado
                            val user = auth.currentUser

                            // Guardar los datos del usuario en Firestore
                            val userData = hashMapOf(
                                "uid" to user?.uid,
                                "email" to user?.email,
                                "provider" to "Email/Password",
                                "creationDate" to System.currentTimeMillis().toString()

                            )

                            db.collection("usuarios").document(user?.uid!!)
                                .set(userData)
                                .addOnSuccessListener {
                                    Toast.makeText(context, "Usuario registrado y guardado", Toast.LENGTH_SHORT).show()
                                    navController.navigate("login") // Navegar al login
                                }
                                .addOnFailureListener {
                                    Toast.makeText(context, "Error al guardar en Firestore", Toast.LENGTH_SHORT).show()
                                }
                        } else {
                            Toast.makeText(context, "Error al registrar en Firebase Auth", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text(text = "REGISTRARME")
        }
        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = {
            navController.navigate("login")
        }) {
            Text(text = "¿Ya tienes una cuenta? Ingresa")
        }
    }
}
package com.duoc.soypuenteapp.pages

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.duoc.soypuenteapp.AuthViewModel
import com.duoc.soypuenteapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun LoginPage(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel) {

    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance() // Firebase Auth
    val db = FirebaseFirestore.getInstance() // Firestore

    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo de la aplicación",
            modifier = Modifier.height(200.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campos de entrada
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = {
                Text(text = "Correo")
            }
        )
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = {
                Text(text = "Contraseña")
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón de inicio de sesión
        Button(onClick = {
            // Iniciar sesión con Firebase Auth
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Obtener el usuario actual
                        val user = auth.currentUser

                        // Obtener los datos del usuario desde Firestore
                        db.collection("usuarios").document(user?.uid!!)
                            .get()
                            .addOnSuccessListener { document ->
                                if (document.exists()) {
                                    // Extraer los datos del documento y navegar a la página de inicio
                                    val nombre = document.getString("nombre") ?: "No Name"
                                    val direccion = document.getString("direccion") ?: "No Address"
                                    Toast.makeText(context, "Bienvenido $nombre", Toast.LENGTH_SHORT).show()
                                    navController.navigate("home") // Navegar a la página de inicio
                                } else {
                                    Toast.makeText(context, "No se encontraron datos del usuario", Toast.LENGTH_SHORT).show()
                                }
                            }
                            .addOnFailureListener {
                                Toast.makeText(context, "Error al obtener datos del usuario", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        Toast.makeText(context, "Error en el inicio de sesión: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        }) {
            Text(text = "ENTRAR")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Botón para registrarse
        TextButton(onClick = {
            navController.navigate("signup")
        }) {
            Text(text = "¿No tienes una cuenta? ¡Regístrate!")
        }
    }
}


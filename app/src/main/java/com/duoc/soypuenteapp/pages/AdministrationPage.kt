package com.duoc.soypuenteapp.pages

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.duoc.soypuenteapp.AuthViewModel
import com.duoc.soypuenteapp.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun AdministrationPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    var selectedUser by remember { mutableStateOf<Usuario?>(null) }
    var userList by remember { mutableStateOf<List<Usuario>>(emptyList()) }

    // Cargar lista de usuarios
    LaunchedEffect(Unit) {
        db.collection("usuarios").get()
            .addOnSuccessListener { result ->
                userList = result.map { document ->
                    document.toObject(Usuario::class.java)
                }
            }
            .addOnFailureListener {
                Toast.makeText(context, "Error al cargar usuarios", Toast.LENGTH_SHORT).show()
            }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Administrar Usuarios", fontSize = 25.sp)

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar lista de usuarios
        LazyColumn(modifier = Modifier.fillMaxHeight(0.5f)) {
            items(userList) { user ->
                UserRow(user, onEditClick = {
                    selectedUser = it // Seleccionamos el usuario para editar
                }, onDeleteClick = {
                    deleteUser(it, db, auth, context) // Llamar a la función para eliminar
                })
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Si hay un usuario seleccionado, mostramos el formulario para editarlo
        selectedUser?.let { user ->
            EditUserForm(
                user = user,
                onSaveClick = {
                    updateUser(it, db, context)
                    selectedUser = null // Limpiar la selección después de guardar
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para volver al Home
        TextButton(onClick = {
            navController.navigate("home") {
                popUpTo(0)
            }
        }) {
            Text(text = "Volver al Home")
        }
    }
}

@Composable
fun UserRow(
    user: Usuario,
    onEditClick: (Usuario) -> Unit,
    onDeleteClick: (Usuario) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = user.email, fontSize = 18.sp)

        Row {
            Button(onClick = { onEditClick(user) }) {
                Text(text = "Editar")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { onDeleteClick(user) }) {
                Text(text = "Eliminar")
            }
        }
    }
}

@Composable
fun EditUserForm(
    user: Usuario,
    onSaveClick: (Usuario) -> Unit
) {
    var nombre by remember { mutableStateOf(user.nombre) }
    var direccion by remember { mutableStateOf(user.direccion) }
    var email by remember { mutableStateOf(user.email) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = direccion,
            onValueChange = { direccion = it },
            label = { Text("Dirección") },
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo") },
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val updatedUser = user.copy(nombre = nombre, direccion = direccion, email = email)
            onSaveClick(updatedUser)
        }) {
            Text(text = "Guardar Cambios")
        }
    }
}

fun updateUser(user: Usuario, db: FirebaseFirestore, context: android.content.Context) {
    db.collection("usuarios").document(user.uid)
        .set(user)
        .addOnSuccessListener {
            Toast.makeText(context, "Usuario actualizado", Toast.LENGTH_SHORT).show()
        }
        .addOnFailureListener {
            Toast.makeText(context, "Error al actualizar", Toast.LENGTH_SHORT).show()
        }
}

fun deleteUser(user: Usuario, db: FirebaseFirestore, auth: FirebaseAuth, context: android.content.Context) {
    // Eliminar el usuario de Firestore
    db.collection("usuarios").document(user.uid).delete()
        .addOnSuccessListener {
            Toast.makeText(context, "Usuario eliminado de Firestore", Toast.LENGTH_SHORT).show()
        }
        .addOnFailureListener {
            Toast.makeText(context, "Error al eliminar en Firestore", Toast.LENGTH_SHORT).show()
        }

    // Eliminar el usuario de Firebase Authentication
    auth.currentUser?.delete()?.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            Toast.makeText(context, "Usuario eliminado de FirebaseAuth", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Error al eliminar en FirebaseAuth", Toast.LENGTH_SHORT).show()
        }
    }
}

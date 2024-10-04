package com.duoc.soypuenteapp.pages

import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.duoc.soypuenteapp.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import java.util.*

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel,
    context: Context
) {
    var textToSpeak by remember { mutableStateOf("") }
    var tts: TextToSpeech? by remember { mutableStateOf(null) }
    var ttsReady by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        tts = TextToSpeech(context) { status ->
            if (status != TextToSpeech.ERROR) {
                // Configuramos el idioma en español
                val result = tts?.setLanguage(Locale("es", "ES")) // Español de España
                if (result == TextToSpeech.LANG_AVAILABLE || result == TextToSpeech.LANG_COUNTRY_AVAILABLE) {
                    ttsReady = true
                }
            }
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Escribe y reproduce", fontSize = 25.sp)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = textToSpeak,
            onValueChange = { textToSpeak = it },
            label = { Text("Texto para reproducir") },
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (ttsReady) {
                tts?.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null, null)
            } else {
                // Aquí puedes manejar el caso de que TextToSpeech no esté listo
            }
        }) {
            Text(text = "Reproducir Texto")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón de Cerrar Sesión
        TextButton(onClick = {
            // Cerrar sesión de Firebase
            FirebaseAuth.getInstance().signOut()
            // Navegar al login
            navController.navigate("login") {
                popUpTo(0) // Esto limpia el stack de navegación para evitar regresar al home
            }
        }) {
            Text(text = "Cerrar sesión")
        }
    }

    // Cleanup TextToSpeech cuando el Composable se elimina
    DisposableEffect(Unit) {
        onDispose {
            tts?.shutdown()
        }
    }
}

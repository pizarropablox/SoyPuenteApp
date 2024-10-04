package com.duoc.soypuenteapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.platform.LocalContext
import com.duoc.soypuenteapp.pages.AdministrationPage
import com.duoc.soypuenteapp.pages.HomePage
import com.duoc.soypuenteapp.pages.LoginPage
import com.duoc.soypuenteapp.pages.SignupPage

@Composable
fun MyAppNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel) {
    val navController = rememberNavController()
    val context = LocalContext.current // Obtenemos el contexto

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginPage(modifier = modifier, navController = navController, authViewModel = authViewModel)
        }
        composable("signup") {
            SignupPage(modifier = modifier, navController = navController, authViewModel = authViewModel)
        }
        composable("home") {
            // Pasamos el contexto a HomePage
            HomePage(modifier = modifier, navController = navController, authViewModel = authViewModel, context = context)
        }
        composable("administration") {
            // Definimos la ruta para la página de administración
            AdministrationPage(modifier = modifier, navController = navController, authViewModel = authViewModel)
        }

    }
}
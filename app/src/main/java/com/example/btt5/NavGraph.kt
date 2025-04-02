package com.example.btt5

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth

@Composable
fun NavGraph(auth: FirebaseAuth, googleSignInClient: GoogleSignInClient, activity: MainActivity) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController, auth, googleSignInClient, activity) }
        composable("profile") { ProfileScreen(navController, auth) }
        composable("register") { RegisterScreen(navController, auth) }
    }
}

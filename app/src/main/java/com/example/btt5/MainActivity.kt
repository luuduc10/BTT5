package com.example.btt5

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.btt5.ui.theme.BTT5Theme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) // Dùng client ID từ `google-services.json`
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        setContent {
            BTT5Theme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "login") {
                    composable("login") {
                        LoginScreen(navController, auth, googleSignInClient, this@MainActivity)
                    }
                }
            }
        }
    }

    fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) { // Sửa lại tên lỗi cho đúng
                Log.w("Google Sign In", "SignInResult: Failed Code=" + e.statusCode)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Log.d("Firebase Auth", "signInWithCredential: success")
                val user = auth.currentUser
                // Chuyển đến màn hình Profile
                val intent = Intent(this, ProfileActivity::class.java)
                intent.putExtra("userName", user?.displayName)
                intent.putExtra("userEmail", user?.email)
                startActivity(intent)
                finish() // Kết thúc MainActivity để tránh quay lại màn hình login
            } else {
                Log.w("Firebase Auth", "signInWithCredential: failure", task.exception)
            }
        }
    }


    companion object {
        const val RC_SIGN_IN = 9001 // Bỏ `private` để có thể truy cập từ `LoginScreen.kt`
    }
}

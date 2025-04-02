package com.example.btt5

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

@Composable
fun LoginScreen(navController: NavController, auth: FirebaseAuth, googleSignInClient: GoogleSignInClient, context: Context) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        // **Thêm nền xanh nhạt phía sau logo**
        Box(
            modifier = Modifier
                .size(180.dp)
                .background(Color(0xFFE3F2FD), shape = RoundedCornerShape(16.dp)), // Màu nền xanh nhạt
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.uth_logo),
                contentDescription = "UTH Logo",
                modifier = Modifier.size(140.dp)// Điều chỉnh kích thước logo
            )
        }

        Spacer(modifier = Modifier.height(20.dp).align (Alignment.CenterHorizontally))

        // **Hiển thị tên ứng dụng**
        Text(
            text = "SmartTasks",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A73E8)
        )

        Text(
            text = "A simple and efficient to-do app",
            fontSize = 16.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.weight(1f))

        // **Tiêu đề đăng nhập**
        Text(text = "Welcome", fontSize = 22.sp, fontWeight = FontWeight.Bold)

        Text(
            text = "Ready to explore? Log in to get started.",
            fontSize = 16.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(40.dp))
        // **Nút đăng nhập với Google**
        Button(
            onClick = { signInWithGoogle(context, googleSignInClient) },
            colors = ButtonDefaults.buttonColors(Color.White),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                .padding(8.dp),
            shape = RoundedCornerShape(12.dp) // Bo góc mềm mại
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_google),
                contentDescription = "Google Logo",
                modifier = Modifier.size(28.dp) // Tăng kích thước icon
            )
            Spacer(modifier = Modifier.width(12.dp)) // Tăng khoảng cách với chữ
            Text(text = "SIGN IN WITH GOOGLE", color = Color.Black, fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(180.dp))
    }
}



// Hàm đăng nhập Google
private fun signInWithGoogle(context: Context, googleSignInClient: GoogleSignInClient) {
    val signInIntent = googleSignInClient.signInIntent
    (context as MainActivity).startActivityForResult(signInIntent, MainActivity.RC_SIGN_IN)
}

fun signInWithGoogle(context: Context, auth: FirebaseAuth) {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("535693000946-mp6oipdnmu9c582glsrfe91meoie1nlb.apps.googleusercontent.com") // Thay bằng Client ID của bạn
        .requestEmail()
        .build()

    val googleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(context, gso)
    val signInIntent = googleSignInClient.signInIntent

    if (context is MainActivity) {
        context.startActivityForResult(signInIntent, MainActivity.RC_SIGN_IN)
    }
}

// Xử lý kết quả đăng nhập Google trong MainActivity
fun handleGoogleSignInResult(requestCode: Int, data: Intent?, auth: FirebaseAuth) {
    if (requestCode == MainActivity.RC_SIGN_IN) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)!!
            val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
            auth.signInWithCredential(credential).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Firebase Auth", "signInWithCredential: success")
                    // Chuyển hướng sang màn hình chính
                } else {
                    Log.w("Firebase Auth", "signInWithCredential: failure", task.exception)
                }
            }
        } catch (e: ApiException) {
            Log.w("Google Sign In", "SignInResult: Failed Code=${e.statusCode}")
        }
    }
}

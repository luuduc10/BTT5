package com.example.btt5

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileScreen(navController: NavController, auth: FirebaseAuth) {
    val user = auth.currentUser

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Nút quay lại
        IconButton(
            onClick = { navController.popBackStack() }, // Quay lại màn hình trước
            modifier = Modifier.align(Alignment.Start)
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.Blue)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Hiển thị ảnh đại diện
        Box(contentAlignment = Alignment.BottomEnd) {
            Image(
                painter = user?.photoUrl?.let { rememberAsyncImagePainter(it) }
                    ?: painterResource(id = R.drawable.uth_logo), // Ảnh mặc định
                contentDescription = "Profile Picture",
                modifier = Modifier.size(100.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Trường nhập tên
        OutlinedTextField(
            value = user?.displayName ?: "No Name",
            onValueChange = {},
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Trường nhập email
        OutlinedTextField(
            value = user?.email ?: "No Email",
            onValueChange = {},
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Nút Back
        Button(
            onClick = { navController.popBackStack() }, // Quay lại màn hình trước
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(text = "Back", color = Color.White, fontSize = 16.sp)
        }
    }
}

package com.example.btt5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProfileScreen()
        }
    }
}

@Composable
fun ProfileScreen() {
    val user = FirebaseAuth.getInstance().currentUser

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Nút quay lại
        IconButton(
            onClick = { /* TODO: Quay lại màn hình trước */ },
            modifier = Modifier.align(Alignment.Start)
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.Blue)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Ảnh đại diện
        Box(contentAlignment = Alignment.BottomEnd) {
            Image(
                painter = user?.photoUrl?.let { rememberAsyncImagePainter(it) }
                    ?: painterResource(id = R.drawable.uth_logo), // Ảnh mặc định nếu không có
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.Gray, CircleShape)
            )

            // Biểu tượng camera
            IconButton(
                onClick = { /* TODO: Thêm logic thay đổi ảnh đại diện */ },
                modifier = Modifier
                    .size(28.dp)
                    .background(Color.White, CircleShape)
                    .padding(4.dp)
            ) {
                Icon(Icons.Default.PhotoCamera, contentDescription = "Change Profile Picture")
            }
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
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Trường nhập ngày sinh
        OutlinedTextField(
            value = "23/05/1995", // Giá trị tạm thời
            onValueChange = {},
            label = { Text("Date of Birth") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = { /* TODO: Mở DatePicker */ }) {
                    Icon(painterResource(id = R.drawable.ic_calendar), contentDescription = "Calendar")
                }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Nút Back
        Button(
            onClick = { /* TODO: Điều hướng về màn hình trước */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(text = "Back", color = Color.White, fontSize = 16.sp)
        }
    }
}

package com.social.connectMe.features.login.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {

    val gradient = Brush.horizontalGradient(
        colors = listOf(Color(0xFF8E5CF6), Color(0xFFE85D75))
    )

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }



    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0F1222))
            .padding(WindowInsets.statusBars.asPaddingValues())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(40.dp))

        // Top Icon
        Box(
            modifier = Modifier
                .size(70.dp)
                .background(gradient, RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.material3.Icon(
                imageVector = Icons.Default.AccountBox,
                contentDescription = null,
                tint = Color.White
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Welcome back",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Text(
            text = "Sign in to continue",
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(30.dp))

        // EMAIL
        Text("Email", color = Color.Gray, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            leadingIcon = {
                Icon(Icons.Default.Email, null)
            },
            placeholder = { Text("you@example.com") },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = textFieldColors()
        )

        Spacer(modifier = Modifier.height(20.dp))

        // PASSWORD
        Text("Password", color = Color.Gray, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            leadingIcon = {
                androidx.compose.material3.Icon(Icons.Default.Lock, null)
            },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    androidx.compose.material3.Icon(
                        imageVector = if (passwordVisible)
                            Icons.Default.Lock
                        else Icons.Default.Lock,
                        contentDescription = null
                    )
                }
            },
            visualTransformation = if (passwordVisible)
                VisualTransformation.None
            else PasswordVisualTransformation(),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = textFieldColors()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Forgot password?",
            color = Color(0xFFB388FF),
            modifier = Modifier.align(Alignment.End)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // SIGN IN BUTTON
        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(20.dp),
            contentPadding = PaddingValues(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(gradient),
                contentAlignment = Alignment.Center
            ) {
                Text("Sign In", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }

//        Spacer(modifier = Modifier.height(24.dp))
//
//
//
//        Spacer(modifier = Modifier.height(20.dp))



       Spacer(modifier = Modifier.weight(1f))

        //Spacer(modifier = Modifier.height(10.dp))

        Row {
            Text("Don't have an account? ", color = Color.Gray)
            Text("Sign up", color = Color(0xFFB388FF))
        }
    }
}

@Composable
fun SocialButton(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth(1f)
            .height(55.dp)
            .border(1.dp, Color.Gray.copy(0.3f), RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(text, color = Color.White)
    }
}

@Composable
fun textFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = Color.Gray.copy(0.4f),
    unfocusedBorderColor = Color.Gray.copy(0.2f),
    cursorColor = Color.White,
    focusedTextColor = Color.White,
    unfocusedTextColor = Color.White,
    focusedLeadingIconColor = Color.Gray,
    unfocusedLeadingIconColor = Color.Gray
)
package com.social.connectMe.ui.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.R
import androidx.hilt.navigation.compose.hiltViewModel
import com.social.connectMe.core.components.textFieldColors
import com.social.connectMe.ui.theme.ThemeMode
import java.util.logging.Logger

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onNavigateToSettings: () -> Unit = {},
    onLoginSuccess: () -> Unit = {},
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    val gradient = Brush.horizontalGradient(
        colors = listOf(Color(0xFFFFFFFF), Color(0xFFF2F5F8))
    )
    Color(0xFF0F1222)

    Log.d("LoginScreen", "statusBars: ${WindowInsets.statusBars}")

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(gradient)
    ) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(WindowInsets.statusBars.asPaddingValues())
                .padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            Image(
                painter = painterResource(com.social.connectMe.R.drawable.login_logo),
                contentDescription = "App Logo",
                modifier = Modifier.size(200.dp),
                contentScale = ContentScale.Inside
            )

            Text(
                text = "Welcome back",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "Sign in to continue", color = Color.Gray
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text("Email", color = Color.Gray, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = state.email,
                onValueChange = viewModel::onEmailChange,
                leadingIcon = { Icon(Icons.Default.Email, null) },
                placeholder = { Text("you@example.com") },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text("Password", color = Color.Gray, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = state.password,
                onValueChange = viewModel::onPasswordChange,
                leadingIcon = { Icon(Icons.Default.Lock, null) },
                trailingIcon = {
                    IconButton(onClick = viewModel::togglePasswordVisibility) {
                        Icon(
                            imageVector = Icons.Default.Lock, contentDescription = null
                        )
                    }
                },
                visualTransformation = if (state.isPasswordVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = onLoginSuccess, // For now, just navigate on click
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Sign In", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.weight(1f))

            Row {
                Text("Don't have an account? ", color = Color.Gray)
                Text("Sign up", color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

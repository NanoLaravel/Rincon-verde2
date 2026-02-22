package com.example.rincon_verde2.ui.feature.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rincon_verde2.domain.model.AuthState
import com.example.rincon_verde2.ui.theme.Strings
import com.example.rincon_verde2.ui.theme.Spacing

@Composable
fun AuthScreen(
    mode: String = "login",
    onLoginSuccess: (email: String) -> Unit = { },
    onSignUpSuccess: (email: String, displayName: String) -> Unit = { _, _ -> },
    onNavigateBack: () -> Unit = { },
    viewModel: AuthViewModel = hiltViewModel()
) {
    var isLoginMode by remember { mutableStateOf(mode == "login") }
    val authState by viewModel.authState.collectAsState()
    
    // Observar cambios en authState
    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Authenticated -> {
                val user = (authState as AuthState.Authenticated).user
                if (isLoginMode) {
                    onLoginSuccess(user.email)
                } else {
                    onSignUpSuccess(user.email, user.displayName)
                }
            }
            is AuthState.Error -> {
                // El error se muestra en el formulario
            }
            else -> {}
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Spacing.spacingXxl),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(Spacing.spacingMassive))
        
        Text(
            text = Strings.authAppTitle,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(Spacing.spacingLg))
        
        Text(
            text = if (isLoginMode) Strings.authWelcomeBack else Strings.authJoinCommunity,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(Spacing.spacingMassive))
        
        if (isLoginMode) {
            LoginForm(
                viewModel = viewModel,
                authState = authState,
                onToggleMode = { isLoginMode = false }
            )
        } else {
            SignUpForm(
                viewModel = viewModel,
                authState = authState,
                onToggleMode = { isLoginMode = true }
            )
        }
    }
}

@Composable
private fun LoginForm(
    viewModel: AuthViewModel,
    authState: AuthState,
    onToggleMode: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    
    OutlinedTextField(
        value = email,
        onValueChange = { email = it },
        label = { Text(Strings.authEmail) },
        leadingIcon = { Icon(Icons.Filled.Email, contentDescription = Strings.cdEmail) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        enabled = authState !is AuthState.Loading
    )
    
    Spacer(modifier = Modifier.height(Spacing.spacingLg))
    
    OutlinedTextField(
        value = password,
        onValueChange = { password = it },
        label = { Text(Strings.authPassword) },
        leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = Strings.cdPassword) },
        visualTransformation = PasswordVisualTransformation(),
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        enabled = authState !is AuthState.Loading
    )
    
    // Mostrar error si existe
    if (authState is AuthState.Error) {
        Spacer(modifier = Modifier.height(Spacing.spacingLg))
        Text(
            text = (authState as AuthState.Error).message,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.fillMaxWidth()
        )
    }
    
    Spacer(modifier = Modifier.height(Spacing.spacingXxxl))
    
    Button(
        onClick = {
            viewModel.login(email.trim(), password.trim())
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(Spacing.spacingMassive),
        enabled = email.isNotBlank() && password.isNotBlank() && authState !is AuthState.Loading
    ) {
        if (authState is AuthState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .height(Spacing.spacingXl)
                    .align(Alignment.CenterVertically)
            )
        } else {
            Text(Strings.authLogin)
        }
    }
    
    Spacer(modifier = Modifier.height(Spacing.spacingXxl))
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = Strings.authNoAccount,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        TextButton(onClick = onToggleMode) {
            Text(Strings.authCreateAccount)
        }
    }
}

@Composable
private fun SignUpForm(
    viewModel: AuthViewModel,
    authState: AuthState,
    onToggleMode: () -> Unit = {}
) {
    var displayName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    
    OutlinedTextField(
        value = displayName,
        onValueChange = { displayName = it },
        label = { Text(Strings.authFullName) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        enabled = authState !is AuthState.Loading
    )
    
    Spacer(modifier = Modifier.height(Spacing.spacingLg))
    
    OutlinedTextField(
        value = email,
        onValueChange = { email = it },
        label = { Text(Strings.authEmail) },
        leadingIcon = { Icon(Icons.Filled.Email, contentDescription = Strings.cdEmail) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        enabled = authState !is AuthState.Loading
    )
    
    Spacer(modifier = Modifier.height(Spacing.spacingLg))
    
    OutlinedTextField(
        value = password,
        onValueChange = { password = it },
        label = { Text(Strings.authPassword) },
        leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = Strings.cdPassword) },
        visualTransformation = PasswordVisualTransformation(),
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        enabled = authState !is AuthState.Loading
    )
    
    Spacer(modifier = Modifier.height(Spacing.spacingLg))
    
    OutlinedTextField(
        value = confirmPassword,
        onValueChange = { confirmPassword = it },
        label = { Text(Strings.authConfirmPassword) },
        leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = Strings.cdPassword) },
        visualTransformation = PasswordVisualTransformation(),
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        enabled = authState !is AuthState.Loading
    )
    
    // Mostrar error si existe
    if (authState is AuthState.Error) {
        Spacer(modifier = Modifier.height(Spacing.spacingLg))
        Text(
            text = (authState as AuthState.Error).message,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.fillMaxWidth()
        )
    }
    
    Spacer(modifier = Modifier.height(Spacing.spacingXxxl))
    
    Button(
        onClick = {
            viewModel.signUp(email.trim(), password.trim(), displayName.trim(), confirmPassword.trim())
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(Spacing.spacingMassive),
        enabled = displayName.isNotBlank() && email.isNotBlank() && 
                  password.isNotBlank() && confirmPassword.isNotBlank() &&
                  authState !is AuthState.Loading
    ) {
        if (authState is AuthState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .height(Spacing.spacingXl)
                    .align(Alignment.CenterVertically)
            )
        } else {
            Text(Strings.authSignUp)
        }
    }
    
    Spacer(modifier = Modifier.height(Spacing.spacingXxl))
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = Strings.authHaveAccount,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        TextButton(onClick = onToggleMode) {
            Text(Strings.authLoginPrompt)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuthScreenLoginPreview() {
    AuthScreen(mode = "login")
}

@Preview(showBackground = true)
@Composable
fun AuthScreenSignUpPreview() {
    AuthScreen(mode = "signup")
}

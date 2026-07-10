package com.example.rincon_verde2.ui.feature.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
/* 
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
*/
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
    val context = androidx.compose.ui.platform.LocalContext.current

    // Launcher para Google Sign-In
    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            // Este idToken es el que se envía al backend
            account.idToken?.let { token ->
                viewModel.socialLogin(token, "google")
            }
        } catch (e: ApiException) {
            android.util.Log.e("AuthScreen", "Google Sign-In failed", e)
        }
    }

    // Configuración de Google
    val gso = remember {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("1099090565884-bjb9o2lp4nvvqq95k41stuj8rfifnvk5.apps.googleusercontent.com")
            .requestEmail()
            .build()
    }
    val googleSignInClient = remember { GoogleSignIn.getClient(context, gso) }

    /*
    // Manejo de Facebook
    val callbackManager = remember { CallbackManager.Factory.create() }
    DisposableEffect(Unit) {
        try {
            LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    val token = result.accessToken.token
                    viewModel.socialLogin(token, "facebook")
                }
                override fun onCancel() { }
                override fun onError(error: FacebookException) {
                    android.util.Log.e("AuthScreen", "Facebook login error", error)
                }
            })
        } catch (e: Exception) {
            android.util.Log.e("AuthScreen", "Facebook callback registration failed", e)
        }
        onDispose { }
    }
    */
    
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

        Spacer(modifier = Modifier.height(Spacing.spacingXl))
        
        SocialLoginSection(
            onGoogleClick = { googleSignInLauncher.launch(googleSignInClient.signInIntent) }
        )
        
        Spacer(modifier = Modifier.height(Spacing.spacingXl))
    }
}

@Composable
private fun SocialLoginSection(
    onGoogleClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = Spacing.spacingMd)
        ) {
            HorizontalDivider(modifier = Modifier.weight(1f))
            Text(
                text = " o continuar con ",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = Spacing.spacingMd)
            )
            HorizontalDivider(modifier = Modifier.weight(1f))
        }

        OutlinedButton(
            onClick = onGoogleClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.spacingXl),
            shape = MaterialTheme.shapes.medium,
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = com.example.rincon_verde2.R.drawable.ic_google),
                    contentDescription = "Google",
                    modifier = Modifier.size(20.dp),
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Google", color = Color.Black)
            }
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
    var passwordVisible by remember { mutableStateOf(false) }
    
    OutlinedTextField(
        value = email,
        onValueChange = { email = it },
        label = { Text(Strings.authEmail) },
        leadingIcon = { Icon(Icons.Filled.Email, contentDescription = Strings.cdEmail, tint = MaterialTheme.colorScheme.primary) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        textStyle = TextStyle(color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Normal),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = Color.Gray,
            cursorColor = MaterialTheme.colorScheme.primary
        ),
        enabled = authState !is AuthState.Loading
    )
    
    Spacer(modifier = Modifier.height(Spacing.spacingLg))
    
    OutlinedTextField(
        value = password,
        onValueChange = { password = it },
        label = { Text(Strings.authPassword) },
        leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = Strings.cdPassword, tint = MaterialTheme.colorScheme.primary) },
        trailingIcon = {
            val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
            val description = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = image, contentDescription = description, tint = Color.Gray)
            }
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        textStyle = TextStyle(color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Normal),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = Color.Gray,
            cursorColor = MaterialTheme.colorScheme.primary
        ),
        enabled = authState !is AuthState.Loading
    )
    
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
        onClick = { viewModel.login(email.trim(), password.trim()) },
        modifier = Modifier
            .fillMaxWidth()
            .height(Spacing.spacingMassive),
        enabled = email.isNotBlank() && password.isNotBlank() && authState !is AuthState.Loading
    ) {
        if (authState is AuthState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(Spacing.spacingXl),
                color = MaterialTheme.colorScheme.onPrimary
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
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    
    OutlinedTextField(
        value = displayName,
        onValueChange = { displayName = it },
        label = { Text(Strings.authFullName) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        textStyle = TextStyle(color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Normal),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = Color.Gray,
            cursorColor = MaterialTheme.colorScheme.primary
        ),
        enabled = authState !is AuthState.Loading
    )
    
    Spacer(modifier = Modifier.height(Spacing.spacingLg))
    
    OutlinedTextField(
        value = email,
        onValueChange = { email = it },
        label = { Text(Strings.authEmail) },
        leadingIcon = { Icon(Icons.Filled.Email, contentDescription = Strings.cdEmail, tint = MaterialTheme.colorScheme.primary) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        textStyle = TextStyle(color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Normal),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = Color.Gray,
            cursorColor = MaterialTheme.colorScheme.primary
        ),
        enabled = authState !is AuthState.Loading
    )
    
    Spacer(modifier = Modifier.height(Spacing.spacingLg))
    
    OutlinedTextField(
        value = password,
        onValueChange = { password = it },
        label = { Text(Strings.authPassword) },
        leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = Strings.cdPassword, tint = MaterialTheme.colorScheme.primary) },
        trailingIcon = {
            val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
            val description = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = image, contentDescription = description, tint = Color.Gray)
            }
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        textStyle = TextStyle(color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Normal),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = Color.Gray,
            cursorColor = MaterialTheme.colorScheme.primary
        ),
        enabled = authState !is AuthState.Loading
    )
    
    Spacer(modifier = Modifier.height(Spacing.spacingLg))
    
    OutlinedTextField(
        value = confirmPassword,
        onValueChange = { confirmPassword = it },
        label = { Text(Strings.authConfirmPassword) },
        leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = Strings.cdPassword, tint = MaterialTheme.colorScheme.primary) },
        trailingIcon = {
            val image = if (confirmPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
            val description = if (confirmPasswordVisible) "Ocultar contraseña" else "Mostrar contraseña"
            IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                Icon(imageVector = image, contentDescription = description, tint = Color.Gray)
            }
        },
        visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        textStyle = TextStyle(color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Normal),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = Color.Gray,
            cursorColor = MaterialTheme.colorScheme.primary
        ),
        enabled = authState !is AuthState.Loading
    )
    
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
                modifier = Modifier.size(Spacing.spacingXl),
                color = MaterialTheme.colorScheme.onPrimary
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

package com.example.rincon_verde2.ui.feature.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rincon_verde2.ui.theme.RinconVerdeTheme
import com.example.rincon_verde2.ui.theme.Strings
import com.example.rincon_verde2.ui.theme.Spacing
import com.example.rincon_verde2.ui.theme.AnimationDuration
import com.example.rincon_verde2.ui.theme.GreenPrimary
import com.example.rincon_verde2.ui.theme.GreenPrimaryDark
import kotlinx.coroutines.delay

/**
 * Technical Splash Screen - Shows animated logo for 1 second
 * then navigates to onboarding or directly to auth/home based on session state.
 * 
 * This is a minimal, branded loading screen that:
 * 1. Shows the app logo with a scale + fade animation
 * 2. Displays the app name and tagline
 * 3. Waits 1 second before navigation
 */
@Composable
fun SplashScreen(
    onNavigateToOnboarding: () -> Unit = { },
    onNavigateToHome: () -> Unit = { },
    onNavigateToAuth: () -> Unit = { },
    viewModel: SplashViewModel = hiltViewModel()
) {
    val scaleAnimation = remember { Animatable(0.3f) }
    val alphaAnimation = remember { Animatable(0f) }
    val pulseAnimation = remember { Animatable(1f) }
    val sessionState by viewModel.sessionState.collectAsState()

    // Trigger session check when screen loads
    LaunchedEffect(Unit) {
        viewModel.checkSession()
    }

    // Animate logo entrance
    LaunchedEffect(Unit) {
        // Initial scale animation
        scaleAnimation.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 800,
                easing = FastOutSlowInEasing
            )
        )
        
        // Fade in
        alphaAnimation.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 600,
                easing = FastOutSlowInEasing
            )
        )
        
        // Subtle pulse animation
        pulseAnimation.animateTo(
            targetValue = 1.05f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            )
        )
    }

    // Navigate after 2 seconds based on session state
    LaunchedEffect(sessionState) {
        delay(2000) // 2 seconds technical splash
        
        when (sessionState) {
            is SessionState.Authenticated -> {
                onNavigateToHome()
            }
            is SessionState.Unauthenticated -> {
                onNavigateToOnboarding()
            }
            is SessionState.Loading -> {
                // Wait for session check to complete
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                    colors = listOf(
                        GreenPrimary,
                        GreenPrimaryDark
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Animated logo
            Icon(
                imageVector = Icons.Default.Eco,
                contentDescription = Strings.appName,
                modifier = Modifier
                    .size(120.dp)
                    .scale(scaleAnimation.value * pulseAnimation.value)
                    .alpha(alphaAnimation.value),
                tint = Color.White
            )

            Spacer(modifier = Modifier.height(Spacing.spacingXxl))

            // App name
            Text(
                text = "Rincón Verde",
                style = TextStyle(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    letterSpacing = 1.sp
                ),
                modifier = Modifier
                    .alpha(alphaAnimation.value)
            )

            Spacer(modifier = Modifier.height(Spacing.spacingSm))

            // Tagline
            Text(
                text = Strings.splashTagline,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.White.copy(alpha = 0.8f)
                ),
                modifier = Modifier.alpha(alphaAnimation.value)
            )

            Spacer(modifier = Modifier.weight(1f))

            // Loading indicator
            Text(
                text = Strings.splashLoading,
                style = TextStyle(
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.6f)
                ),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .alpha(alphaAnimation.value)
            )

            Spacer(modifier = Modifier.height(Spacing.spacingXxxl))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    RinconVerdeTheme {
        SplashScreen()
    }
}

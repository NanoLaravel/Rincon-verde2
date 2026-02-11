package com.example.rincon_verde2.ui.feature.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardTravel
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.rincon_verde2.ui.theme.Rinconverde2Theme

@Composable
fun SplashScreen(
  onNavigateToHome: () -> Unit = { }
) {
  val scaleAnimation = remember { Animatable(0.5f) }
  val alphaAnimation = remember { Animatable(0f) }

  LaunchedEffect(Unit) {
    // Animar logo (escala + fade in)
    scaleAnimation.animateTo(
      targetValue = 1f,
      animationSpec = tween(durationMillis = 1000)
    )
    alphaAnimation.animateTo(
      targetValue = 1f,
      animationSpec = tween(durationMillis = 1000)
    )

    // Esperar 3 segundos y navegar
    kotlinx.coroutines.delay(3000)
    onNavigateToHome()
  }

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.primary),
    contentAlignment = Alignment.Center
  ) {
    Column(
      modifier = Modifier.fillMaxSize(),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      // Logo animado
      Icon(
        imageVector = Icons.Default.CardTravel,
        contentDescription = "Rincón Verde Logo",
        modifier = Modifier
          .size(120.dp)
          .scale(scaleAnimation.value)
          .alpha(alphaAnimation.value),
        tint = Color.White
      )

      Spacer(modifier = Modifier.height(32.dp))

      // Nombre de la app
      Text(
        text = "Rincón Verde",
        style = TextStyle(
          fontSize = 36.sp,
          fontWeight = FontWeight.Bold,
          color = Color.White
        ),
        modifier = Modifier.alpha(alphaAnimation.value)
      )

      Spacer(modifier = Modifier.height(16.dp))

      // Slogan
      Text(
        text = "Guía de Turismo Local",
        style = TextStyle(
          fontSize = 16.sp,
          fontWeight = FontWeight.Light,
          color = Color.White.copy(alpha = 0.8f)
        ),
        modifier = Modifier.alpha(alphaAnimation.value)
      )

      Spacer(modifier = Modifier.height(48.dp))

      // Características destacadas
      Column(
        modifier = Modifier
          .alpha(alphaAnimation.value),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        FeatureItem("🏠 Descubre lugares", "Restaurantes, hoteles y actividades")
        Spacer(modifier = Modifier.height(16.dp))
        FeatureItem("⭐ Reseñas locales", "Opiniones de visitantes y residentes")
        Spacer(modifier = Modifier.height(16.dp))
        FeatureItem("❤️ Guarda favoritos", "Marca tus lugares preferidos")
        Spacer(modifier = Modifier.height(16.dp))
        FeatureItem("📅 Eventos cercanos", "No te pierdas actividades locales")
      }

      Spacer(modifier = Modifier.weight(1f))

      // Loading indicator
      Text(
        text = "Cargando...",
        style = TextStyle(
          fontSize = 12.sp,
          color = Color.White.copy(alpha = 0.6f)
        ),
        modifier = Modifier
          .align(Alignment.CenterHorizontally)
          .alpha(alphaAnimation.value)
      )

      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}

@Composable
private fun FeatureItem(title: String, description: String) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = title,
      style = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color.White
      ),
      textAlign = TextAlign.Center
    )
    Text(
      text = description,
      style = TextStyle(
        fontSize = 12.sp,
        color = Color.White.copy(alpha = 0.7f)
      ),
      textAlign = TextAlign.Center
    )
  }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
  Rinconverde2Theme {
    SplashScreen()
  }
}

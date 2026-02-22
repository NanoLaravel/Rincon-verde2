package com.example.rincon_verde2.ui.feature.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.rincon_verde2.ui.theme.CornerRadius
import com.example.rincon_verde2.ui.theme.GreenPrimary
import com.example.rincon_verde2.ui.theme.GreenPrimaryDark
import com.example.rincon_verde2.ui.theme.GreenSurface
import com.example.rincon_verde2.ui.theme.RinconVerdeTheme
import com.example.rincon_verde2.ui.theme.Spacing
import com.example.rincon_verde2.ui.theme.Strings
import com.example.rincon_verde2.ui.theme.AnimationDuration
import kotlinx.coroutines.launch

/**
 * Data class representing a single onboarding page
 */
data class OnboardingPage(
    val imageRes: String,
    val title: String,
    val description: String,
    val icon: ImageVector
)

/**
 * Onboarding screen with 3 swipeable pages showcasing the app features.
 * 
 * Image suggestions for Chinácota:
 * - Page 1 (Discover): Panoramic view of Chinácota's mountains, the iconic church, 
 *   or the main plaza with colonial architecture
 * - Page 2 (Stay): Local cabañas, hotels with mountain views, or eco-lodges
 * - Page 3 (Explore): People hiking, local restaurants, or tourists enjoying activities
 * 
 * Place images in: app/src/main/res/drawable/
 * Recommended names: onboarding_1.jpg, onboarding_2.jpg, onboarding_3.jpg
 * Recommended size: 1080x1920 (portrait) or use high-quality webp format
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    onboardingPages: List<OnboardingPage>,
    onComplete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = { onboardingPages.size })
    val coroutineScope = rememberCoroutineScope()
    
    // Animation for content
    val contentAlpha = remember { Animatable(0f) }
    
    LaunchedEffect(Unit) {
        contentAlpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = AnimationDuration.veryLong,
                easing = FastOutSlowInEasing
            )
        )
    }
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Horizontal Pager
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            OnboardingPageContent(
                page = onboardingPages[page],
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(contentAlpha.value)
            )
        }
        
        // Bottom controls overlay
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(bottom = Spacing.spacingXxl)
                .fillMaxWidth()
                .alpha(contentAlpha.value),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Page indicators
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = Spacing.spacingXxl)
            ) {
                repeat(onboardingPages.size) { index ->
                    PageIndicator(
                        isSelected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                    )
                }
            }
            
            // Navigation buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.spacingXxl),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Skip button (only show if not on last page)
                if (pagerState.currentPage < onboardingPages.size - 1) {
                    TextButton(
                        onClick = onComplete
                    ) {
                        Text(
                            text = Strings.onboardingSkip,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.size(1.dp))
                }
                
                // Next/Start button
                Button(
                    onClick = {
                        if (pagerState.currentPage < onboardingPages.size - 1) {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(
                                    page = pagerState.currentPage + 1,
                                    animationSpec = tween(
                                        durationMillis = AnimationDuration.pageTransition,
                                        easing = FastOutSlowInEasing
                                    )
                                )
                            }
                        } else {
                            onComplete()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GreenPrimary
                    ),
                    shape = MaterialTheme.shapes.large
                ) {
                    Text(
                        text = if (pagerState.currentPage < onboardingPages.size - 1) Strings.onboardingNext else Strings.onboardingStart,
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(horizontal = Spacing.spacingMd)
                    )
                }
            }
        }
    }
}

@Composable
private fun OnboardingPageContent(
    page: OnboardingPage,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        // Background Image
        AsyncImage(
            model = page.imageRes,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        
        // Gradient overlay for text readability
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.3f),
                            Color.Black.copy(alpha = 0.8f)
                        ),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY
                    )
                )
        )
        
        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(Spacing.spacingXxl),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Icon
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(GreenPrimary.copy(alpha = 0.9f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = page.icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(Spacing.spacingXxl))
            
            // Title
            Text(
                text = page.title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(Spacing.spacingMd))
            
            // Description
            Text(
                text = page.description,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White.copy(alpha = 0.9f),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = Spacing.spacingLg)
            )
            
            // Extra space for bottom controls
            Spacer(modifier = Modifier.height(120.dp))
        }
    }
}

@Composable
private fun PageIndicator(
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(horizontal = Spacing.spacingXs)
            .size(if (isSelected) 10.dp else 8.dp)
            .clip(CircleShape)
            .background(
                if (isSelected) GreenPrimary
                else Color.White.copy(alpha = 0.5f)
            )
    )
}

/**
 * Default onboarding pages for Chinácota
 */
fun getDefaultOnboardingPages(): List<OnboardingPage> = listOf(
    OnboardingPage(
        imageRes = "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=1080&h=1920&fit=crop",
        title = "Descubre lugares únicos",
        description = "Explora los rincones más hermosos de Chinácota: montañas, cascadas y paisajes que te dejarán sin aliento.",
        icon = Icons.Default.Explore
    ),
    OnboardingPage(
        imageRes = "https://images.unsplash.com/photo-1566073771259-6a8506099945?w=1080&h=1920&fit=crop",
        title = "Cabañas y experiencias",
        description = "Encuentra cabañas acogedoras, hoteles con vista a las montañas y experiencias inolvidables para tu estadía.",
        icon = Icons.Default.Hotel
    ),
    OnboardingPage(
        imageRes = "https://images.unsplash.com/photo-1555939594-58d7cb561ad1?w=1080&h=1920&fit=crop",
        title = "Reserva y explora",
        description = "Reserva tu lugar favorito, explora nuevas experiencias y comparte tus reseñas con otros viajeros.",
        icon = Icons.Default.Star
    )
)

@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    RinconVerdeTheme {
        OnboardingScreen(
            onboardingPages = getDefaultOnboardingPages(),
            onComplete = {}
        )
    }
}

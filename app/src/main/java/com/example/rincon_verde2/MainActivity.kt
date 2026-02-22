package com.example.rincon_verde2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.rincon_verde2.ui.navigation.RinconVerdeNavGraph
import com.example.rincon_verde2.ui.theme.RinconVerdeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      RinconVerdeTheme {
        val navController = rememberNavController()
        RinconVerdeNavGraph(navController = navController)
      }
    }
  }
}

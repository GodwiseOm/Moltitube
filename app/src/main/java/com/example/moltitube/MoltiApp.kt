package com.example.moltitube

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.compose.MoltiTubeTheme
import com.example.moltitube.Screens.TubeDisplay
import com.example.moltitube.Screens.WelcomeScreen

@Composable
fun MoltiApp(){
    MoltiTubeTheme {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        val padding = innerPadding
      TubeDisplay(modifier = Modifier.statusBarsPadding())

    }}
}
package com.example.moltitube.Screens


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.MoltiTubeTheme


@Composable
fun WelcomeScreen(modifier: Modifier = Modifier,visibility :Boolean = true ) {
    AnimatedVisibility(modifier = modifier, visible = visibility, exit = fadeOut(animationSpec = tween(800))) {

        Box(modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.surface), contentAlignment = Alignment.Center) {
            var widthValue by rememberSaveable { mutableStateOf(1f) }
            var heightValue by rememberSaveable { mutableStateOf(10f) }

            val height by animateDpAsState(
                targetValue = heightValue.dp, animationSpec = tween(2000, delayMillis = 3000),
                label = "animate"
            )

            val width by animateFloatAsState(
                targetValue = widthValue, animationSpec = tween(5000),
                label = "Also animate"
            )

            LaunchedEffect(Unit) {
                widthValue = 0.2f

                heightValue = 40f


            }
            Box(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth(width)
                    .height(height)
                    .background(color = MaterialTheme.colorScheme.primary)
                    .height(12.dp)

            ) {

            }

        }
    }
}

@Preview(showSystemUi = false)
@Composable
fun WeclomeScreenPreview() {
    MoltiTubeTheme {
        WelcomeScreen()
    }
}
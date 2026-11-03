package com.example.appvoltio.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import com.example.appvoltio.R

@Composable
fun IconoRayoAnimado(modifier: Modifier = Modifier) {
    val transicion = rememberInfiniteTransition(label = "rayo")

    val rotacion by transicion.animateFloat(
        initialValue = -12f,
        targetValue = 12f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 700, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "rotacion"
    )

    val escala by transicion.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "escala"
    )

    Icon(
        painter = painterResource(id = R.drawable.ic_rayo),
        contentDescription = "Logo de AppVoltio",
        tint = MaterialTheme.colorScheme.secondary,
        modifier = modifier.graphicsLayer {
            rotationZ = rotacion
            scaleX = escala
            scaleY = escala
        }
    )
}
package com.example.appvoltio.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = AzulVoltio,
    onPrimary = Color.White,
    primaryContainer = AzulVoltioContenedor,
    onPrimaryContainer = AzulVoltio,
    secondary = AmbarVoltio,
    onSecondary = AmbarVoltioTexto,
    background = FondoClaro,
    surface = SuperficieClara,
    surfaceVariant = SuperficieVarianteClara,
    surfaceContainerHighest = AzulVoltioContenedor,
    onSurfaceVariant = TextoSecundarioClaro,
    error = ErrorClaro
)

private val DarkColorScheme = darkColorScheme(
    primary = AzulVoltioOscuro,
    onPrimary = AzulVoltioTextoOscuro,
    secondary = AmbarVoltioOscuro,
    onSecondary = AmbarVoltioTextoOscuro,
    background = FondoOscuro,
    surface = SuperficieOscura,
    surfaceVariant = SuperficieVarianteOscura,
    surfaceContainerHighest = SuperficieVarianteOscura,
    onSurfaceVariant = TextoSecundarioOscuro,
    error = ErrorOscuro
)

@Composable
fun AppVoltioTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
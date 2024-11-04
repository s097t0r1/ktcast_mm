package me.s097t0r1.core.ui_components.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember

@Composable
fun KtCastTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    colors: KtCastColors = if (isDarkTheme) darkColors() else lightColors(),
    typography: KtCastTypography = KtCastTheme.typography,
    Content: @Composable () -> Unit
) {
    val remeberedColors = remember { colors.copy() }.apply { updateFrom(colors) }
    CompositionLocalProvider(
        LocalColors provides remeberedColors,
        LocalContentAlpha provides ContentAlpha.high,
        LocalContentColor provides remeberedColors.textPrimaryColor,
        LocalTypography provides typography
    ) { Content() }
}

object KtCastTheme {

    val colors: KtCastColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val typography: KtCastTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

}
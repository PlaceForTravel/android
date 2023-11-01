package com.easyhz.placeapp.ui.theme

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Neutral0,
    onPrimary = Neutral8,
    secondary = Neutral0,
    onSecondary = Neutral8,
    tertiary = Neutral0,
    onTertiary = Neutral8,
    error = FunctionalRed,
    onError = Neutral8,
    background = Neutral8,
    onBackground = Neutral1,
    outline = Neutral0,
    surface = Neutral8,
    surfaceVariant = Neutral8,
)

private val LightColorScheme = lightColorScheme(
    primary = Neutral8,
    onPrimary = Neutral0,
    secondary = Neutral8,
    onSecondary = Neutral0,
    tertiary = Neutral8,
    onTertiary = Neutral0,
    error = FunctionalRed,
    onError = Neutral0,
    background = Neutral1,
    onBackground = Neutral8,
    outline = Neutral8,
    surface = Neutral0,
    surfaceVariant = Neutral0,
    secondaryContainer = Transparent,
    onSecondaryContainer = Transparent
)

private val AppLightThemeColors = appLightColorScheme()
private val AppDarkThemeColors = appDarkColorScheme()

@Composable
fun PlaceAppTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (isDarkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        isDarkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val appColorScheme = when {
        isDarkTheme -> AppDarkThemeColors
        else -> AppLightThemeColors
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = appColorScheme.transparent.toArgb()
            window.navigationBarColor = appColorScheme.transparent.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !isDarkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = {
            ProvidePlaceAppTheme(appColorScheme = appColorScheme) {
                Surface(content = content)
            }
        },
    )
}

object PlaceAppTheme {
    val colorScheme: PlaceAppColors
        @Composable
        @ReadOnlyComposable
        get() = CustomLocalColorScheme.current
}

@Composable
fun ProvidePlaceAppTheme(
    appColorScheme: PlaceAppColors,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        CustomLocalColorScheme provides appColorScheme,
        content = content
    )
    CompositionLocalProvider(
        LocalRippleTheme provides NoRippleTheme,
        content = content
    )

}

/**
 * primary : 기본 색상
 * secondary : 보조 색상
 * transparent : 투명
 * primaryBorder: border 기본 색상
 * selectedIcon: 선택 아이콘 색상
 * unselectedIcon: 미선택 아이콘 색상
 * background: 배경 색상
 */
open class PlaceAppColors (
    val primary: Color,
    val secondary: Color,
    val transparent: Color,
    val primaryBorder: Color,
    val selectedIcon: Color,
    val unselectedIcon: Color,
    val background: Color,
)

object AppLightColors: PlaceAppColors(
    primary = Sky1,
    secondary = Sky3,
    transparent = Transparent,
    primaryBorder = Neutral4,
    selectedIcon = Neutral8,
    unselectedIcon = Neutral5,
    background = Neutral1
)

object AppDarkColors : PlaceAppColors(
    primary = Sky3,
    secondary = Sky1,
    transparent = Transparent,
    primaryBorder = Neutral4,
    selectedIcon = Neutral0,
    unselectedIcon = Neutral3,
    background = Neutral8
)

fun appLightColorScheme(
    primary: Color = AppLightColors.primary,
    secondary: Color = AppLightColors.secondary,
    transparent: Color = AppLightColors.transparent,
    primaryBorder: Color = AppLightColors.primaryBorder,
    selectedIcon: Color = AppLightColors.selectedIcon,
    unselectedIcon: Color = AppLightColors.unselectedIcon,
    background: Color = AppLightColors.background
) : PlaceAppColors =
    PlaceAppColors (
        primary,
        secondary,
        transparent,
        primaryBorder,
        selectedIcon,
        unselectedIcon,
        background
    )

fun appDarkColorScheme(
    primary: Color = AppDarkColors.primary,
    secondary: Color = AppDarkColors.secondary,
    transparent: Color = AppDarkColors.transparent,
    primaryBorder: Color = AppDarkColors.primaryBorder,
    selectedIcon: Color = AppDarkColors.selectedIcon,
    unselectedIcon: Color = AppDarkColors.unselectedIcon,
    background: Color = AppDarkColors.background
) : PlaceAppColors =
    PlaceAppColors (
        primary,
        secondary,
        transparent,
        primaryBorder,
        selectedIcon,
        unselectedIcon,
        background
    )

@SuppressLint("CompositionLocalNaming")
internal val CustomLocalColorScheme = staticCompositionLocalOf { appLightColorScheme() }

/**
 * NoRipple
 */
private object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.0f,0.0f,0.0f,0.0f)
}
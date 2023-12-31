package com.easyhz.placeapp.ui.theme

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
)

private val LightColorScheme = lightColorScheme(
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
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (isDarkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }

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
            window.statusBarColor = appColorScheme.statusTopBar.toArgb()
            window.navigationBarColor = appColorScheme.statusBottomBar.toArgb()
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
    val secondaryBorder: Color,
    val selectedIcon: Color,
    val unselectedIcon: Color,
    val mainBackground: Color,
    val subBackground: Color,
    val itemBackground: Color,
    val statusTopBar: Color,
    val statusBottomBar: Color,
    val mainText: Color,
    val subText: Color,
    val error: Color
)

object AppLightColors: PlaceAppColors(
    primary = Sky1,
    secondary = Sky3,
    transparent = Transparent,
    primaryBorder = Neutral4,
    secondaryBorder = Neutral5,
    selectedIcon = Neutral8,
    unselectedIcon = Neutral5,
    mainBackground = Neutral1,
    subBackground = Neutral4,
    itemBackground = Neutral9,
    statusTopBar = Neutral4,
    statusBottomBar = Neutral1,
    mainText = Neutral8,
    subText = Neutral5,
    error = FunctionalRed,
)

object AppDarkColors : PlaceAppColors(
    primary = Sky3,
    secondary = Sky1,
    transparent = Transparent,
    primaryBorder = Neutral4,
    secondaryBorder = Neutral4,
    selectedIcon = Neutral0,
    unselectedIcon = Neutral3,
    mainBackground = Neutral6,
    subBackground = Neutral8,
    itemBackground = Neutral5,
    statusTopBar = Neutral8,
    statusBottomBar = Neutral6,
    mainText = Neutral1,
    subText = Neutral2,
    error = FunctionalRed,
)

fun appLightColorScheme(
    primary: Color = AppLightColors.primary,
    secondary: Color = AppLightColors.secondary,
    transparent: Color = AppLightColors.transparent,
    primaryBorder: Color = AppLightColors.primaryBorder,
    secondaryBorder: Color = AppLightColors.secondaryBorder,
    selectedIcon: Color = AppLightColors.selectedIcon,
    unselectedIcon: Color = AppLightColors.unselectedIcon,
    mainBackground: Color = AppLightColors.mainBackground,
    subBackground: Color = AppLightColors.subBackground,
    itemBackground: Color = AppLightColors.itemBackground,
    statusTopBar: Color = AppLightColors.statusTopBar,
    statusBottomBar: Color = AppLightColors.statusBottomBar,
    mainText: Color = AppLightColors.mainText,
    subText: Color = AppLightColors.subText,
    error: Color = AppLightColors.error
) : PlaceAppColors =
    PlaceAppColors (
        primary,
        secondary,
        transparent,
        primaryBorder,
        secondaryBorder,
        selectedIcon,
        unselectedIcon,
        mainBackground,
        subBackground,
        itemBackground,
        statusTopBar,
        statusBottomBar,
        mainText,
        subText,
        error
    )

fun appDarkColorScheme(
    primary: Color = AppDarkColors.primary,
    secondary: Color = AppDarkColors.secondary,
    transparent: Color = AppDarkColors.transparent,
    primaryBorder: Color = AppDarkColors.primaryBorder,
    secondaryBorder: Color = AppDarkColors.secondaryBorder,
    selectedIcon: Color = AppDarkColors.selectedIcon,
    unselectedIcon: Color = AppDarkColors.unselectedIcon,
    mainBackground: Color = AppDarkColors.mainBackground,
    subBackground: Color = AppDarkColors.subBackground,
    itemBackground: Color = AppDarkColors.itemBackground,
    statusTopBar: Color = AppDarkColors.statusTopBar,
    statusBottomBar: Color =  AppDarkColors.statusBottomBar,
    mainText: Color = AppDarkColors.mainText,
    subText: Color = AppDarkColors.subText,
    error: Color = AppDarkColors.error
) : PlaceAppColors =
    PlaceAppColors (
        primary,
        secondary,
        transparent,
        primaryBorder,
        secondaryBorder,
        selectedIcon,
        unselectedIcon,
        mainBackground,
        subBackground,
        itemBackground,
        statusTopBar,
        statusBottomBar,
        mainText,
        subText,
        error
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
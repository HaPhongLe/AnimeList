package com.example.animelist.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class AppDimension(
    val spaceXS: Dp = 4.dp,
    val spaceS: Dp = 8.dp,
    val spaceM: Dp = 16.dp,
    val spaceL: Dp = 20.dp
)

val LocalAppDimensions = staticCompositionLocalOf {
    AppDimension(
        spaceXS = Dp.Unspecified,
        spaceS = Dp.Unspecified,
        spaceM = Dp.Unspecified,
        spaceL = Dp.Unspecified
    )
}

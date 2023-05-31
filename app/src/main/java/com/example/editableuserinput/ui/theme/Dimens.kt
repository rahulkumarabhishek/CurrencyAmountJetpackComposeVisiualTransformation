package com.example.editableuserinput.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimens(
    val dimens0dp: Dp = 0.dp,
    val dimens1dp: Dp = 1.dp,
    val dimens2dp: Dp = 2.dp,
    val dimens4dp: Dp = 4.dp,
    val dimens8dp: Dp = 8.dp,
    val dimens12dp: Dp = 12.dp,
    val dimens16dp: Dp = 16.dp,
    val dimens20dp: Dp = 20.dp,
    val dimens24dp: Dp = 24.dp,
    val dimens28dp: Dp = 28.dp,
    val dimens32dp: Dp = 32.dp,
    val dimens34dp: Dp = 34.dp,
    val dimens48dp: Dp = 48.dp,
    val dimens36dp: Dp = 36.dp,
    val dimens52dp: Dp = 52.dp,
    val dimens110dp: Dp = 110.dp,
    val dimens67dp: Dp = 67.dp,
    val dimens280dp: Dp = 280.dp
)

val LocalDimens = compositionLocalOf { Dimens() }

val MaterialTheme.dimens: Dimens
    @Composable
    @ReadOnlyComposable
    get() = LocalDimens.current

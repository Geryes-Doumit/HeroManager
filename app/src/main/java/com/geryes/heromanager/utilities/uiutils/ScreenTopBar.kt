package com.geryes.heromanager.utilities.uiutils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ScreenTopBar(
    title : String,
    leftContent : @Composable () -> Unit = {},
    rightContent : @Composable () -> Unit = {},
    titlePadding : Int = 14, topMultiplier : Float = 2.4f
) {
    Row(
        modifier = Modifier.background(
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f),
        )
            .fillMaxWidth()
            .padding(
                top = (titlePadding*topMultiplier).dp,
                bottom = titlePadding.dp,
                start = titlePadding.dp,
                end = titlePadding.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        leftContent()
        Text(
            text = title,
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold,
        )
        rightContent()
    }

}
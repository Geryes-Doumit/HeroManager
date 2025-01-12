package com.geryes.heromanager.utilities.uiutils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.geryes.heromanager.R

@Composable
fun ResetButton(
    resetFunction: () -> Unit,
) {
    Icon(
        imageVector = ImageVector.vectorResource(R.drawable.reset_icon),
        contentDescription = "Reset",
        modifier = Modifier.width(46.dp)
            .padding(end = 16.dp)
            .clickable {
                resetFunction()
            },
        tint = MaterialTheme.colorScheme.onSurface,
    )
}
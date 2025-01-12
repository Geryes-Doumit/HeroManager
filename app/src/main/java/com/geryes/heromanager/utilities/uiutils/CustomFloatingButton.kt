package com.geryes.heromanager.utilities.uiutils

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.geryes.heromanager.R

@Composable
fun CustomFloatingButton(onClick: () -> Unit) {
    val contentColor = if (isSystemInDarkTheme()) {
        MaterialTheme.colorScheme.onBackground
    } else {
        MaterialTheme.colorScheme.onSecondaryContainer
    }

    val containerColor = if (isSystemInDarkTheme()) {
        MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.6f)
    } else {
        MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f)
    }
    FloatingActionButton(
        onClick = {
            onClick()
        },
        containerColor = containerColor,
        contentColor = contentColor,
        shape = RoundedCornerShape(23.dp),
    ) {
        androidx.compose.material3.Icon(
            Icons.Filled.Add,
            contentDescription = stringResource(R.string.add_button_content_description),
        )
    }
}
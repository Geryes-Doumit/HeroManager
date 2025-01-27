package com.geryes.heromanager.utilities.uiutils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import androidx.compose.material3.Icon

@Composable
fun GoBackButton(
    navigator: DestinationsNavigator,
    onClick : () -> Unit = {
        navigator.popBackStack()
    }
) {
    Icon(
        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
        contentDescription = "Go Back",
        modifier = Modifier.width(46.dp)
            .padding(end = 16.dp)
            .clickable {
                onClick()
            },
        tint = MaterialTheme.colorScheme.onSurface,
    )
}
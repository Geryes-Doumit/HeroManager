package com.geryes.heromanager.utilities.uiutils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.geryes.heromanager.R

@Composable
fun DeleteButton(
    delFunction: () -> Unit,
) {
    Icon(
        imageVector = Icons.Filled.Delete,
        contentDescription = stringResource(id = R.string.delete_button_content_description),
        modifier = Modifier.width(46.dp)
            .padding(end = 16.dp)
            .clickable {
                delFunction()
            },
        tint = MaterialTheme.colorScheme.onSurface,
    )
}
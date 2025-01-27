package com.geryes.heromanager.utilities.uiutils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.geryes.heromanager.R

@Composable
fun AbandonDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(23.dp),
                ),
        ) {
            Scaffold(
                topBar = {
                    ScreenTopBar(
                        topMultiplier = 1.2f,
                        title = stringResource(R.string.confirm_abandon),
                    )
                },
                bottomBar = {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Button(
                            onClick = onDismiss,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        ) {
                            Text(stringResource(R.string.dialog_cancel_button))
                        }
                        Button(
                            onClick = onConfirm,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        ) {
                            Text(stringResource(R.string.dialog_confirm_button))
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
                    .height(200.dp)
                    .clip(shape = RoundedCornerShape(23.dp))
            ) { innerPadding ->
                Text(
                    text = stringResource(R.string.abandon_dialog_message),
                    modifier = Modifier.padding(innerPadding)
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 0.dp),
                )
            }
        }
    }
}
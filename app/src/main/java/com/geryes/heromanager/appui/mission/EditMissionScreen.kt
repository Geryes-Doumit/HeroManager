package com.geryes.heromanager.appui.mission

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.geryes.heromanager.R
import com.geryes.heromanager.model.MissionState
import com.geryes.heromanager.utilities.uiutils.DeleteButton
import com.geryes.heromanager.utilities.uiutils.DeleteDialog
import com.geryes.heromanager.utilities.uiutils.GoBackButton
import com.geryes.heromanager.utilities.uiutils.ResetButton
import com.geryes.heromanager.utilities.uiutils.ScreenTopBar
import com.geryes.heromanager.utilities.uiutils.showToast
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@Composable
fun EditMissionScreen(
    vm: MissionViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    missionId: Long,
) {
    var showResetDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        vm.setIdAndGetInfo(missionId)
    }

    if (showResetDialog) {
        ResetMissionDialog(
            onConfirm = {
                vm.resetMissionState()
                showResetDialog = false
            },
            onDismiss = { showResetDialog = false }
        )
    }

    if (showDeleteDialog) {
        DeleteDialog(
            onConfirm = {
                showDeleteDialog = false
                vm.deleteMission()
                navigator.popBackStack()
            },
            onDismiss = { showDeleteDialog = false }
        )
    }

    // for the toast
    val context = LocalContext.current
    val toastMessage = stringResource(R.string.delete_ongoing_mission_toast)

    Scaffold(
        topBar = {
            ScreenTopBar(
                leftContent = {
                    GoBackButton(navigator)
                },
                title = vm.missionName.collectAsState().value,
                rightContent = {
                    if (vm.missionState.collectAsState().value == MissionState.COMPLETED) {
                        ResetButton {
                            showResetDialog = true
                        }
                    }
                    DeleteButton {
                        if (vm.missionState.value == MissionState.ONGOING) {
                            showToast(context, toastMessage)
                        }
                        else {
                            showDeleteDialog = true
                        }
                    }
                }
            )
        },
        bottomBar = {
            if (vm.missionState.collectAsState().value == MissionState.PLANNED) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceAround,

                    ) {
                    Button(
                        onClick = {
                            vm.updateMission()
                            navigator.popBackStack()
                        },
                        enabled = !vm.missionNameError.collectAsState().value
                                && !vm.descriptionError.collectAsState().value
                                && !vm.minimumPowerError.collectAsState().value
                                && vm.dataIsDifferent.collectAsState().value,
                    ) {
                        Text(stringResource(R.string.update_mission_button))
                    }
                }
            }
        },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        MissionInputFields(vm, navigator, innerPadding)
    }
}

@Composable
fun ResetMissionDialog(
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
                        title = stringResource(R.string.reset_mission_dialog_title),
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
                    text = stringResource(R.string.reset_mission_dialog_message),
                    modifier = Modifier.padding(innerPadding)
                        .padding(16.dp),
                )
            }
        }
    }
}
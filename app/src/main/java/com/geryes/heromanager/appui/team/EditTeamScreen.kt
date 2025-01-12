package com.geryes.heromanager.appui.team

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.geryes.heromanager.R
import com.geryes.heromanager.model.TeamState
import com.geryes.heromanager.utilities.uiutils.DeleteButton
import com.geryes.heromanager.utilities.uiutils.DeleteDialog
import com.geryes.heromanager.utilities.uiutils.GoBackButton
import com.geryes.heromanager.utilities.uiutils.ScreenTopBar
import com.geryes.heromanager.utilities.uiutils.showToast
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@Composable
fun EditTeamScreen(
    vm: TeamViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    teamId: Long,
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    val teamState = vm.initialTeam.collectAsState().value?.team?.state

    LaunchedEffect(Unit) {
        vm.setIdAndGetInfo(teamId)
    }

    if (showDeleteDialog) {
        DeleteDialog(
            onConfirm = {
                showDeleteDialog = false
                vm.deleteTeam()
                navigator.popBackStack()
            },
            onDismiss = { showDeleteDialog = false }
        )
    }

    // for the toast
    val context = LocalContext.current
    val cannotDeleteTeamToastMsg = stringResource(R.string.cannot_delete_or_edit_team)

    Scaffold (
        topBar = {
            ScreenTopBar(
                leftContent = {
                    GoBackButton(navigator)
                },
                title = vm.teamName.collectAsState(
                    initial = stringResource(R.string.edit_team_title)
                ).value,
                rightContent = {
                    DeleteButton (
                        delFunction = {
                            if (teamState == TeamState.BUSY)
                                showToast(
                                    context,
                                    cannotDeleteTeamToastMsg
                                )
                            else
                                showDeleteDialog = true
                        }
                    )
                }
            )
        },
        bottomBar = {
            if (teamState == TeamState.AVAILABLE) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                ) {
                    Button(
                        onClick = {
                            vm.updateTeam()
                            navigator.popBackStack()
                        },
                        enabled = !vm.teamNameError.collectAsState().value
                                && vm.dataIsDifferent.collectAsState().value,
                    ) {
                        Text(stringResource(R.string.edit_team_button))
                    }
                }
            }
        },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        TeamInputFields(vm, navigator, innerPadding, teamId)
    }
}
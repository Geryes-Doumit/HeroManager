package com.geryes.heromanager.appui.team

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.geryes.heromanager.utilities.uiutils.DeleteButton
import com.geryes.heromanager.utilities.uiutils.GoBackButton
import com.geryes.heromanager.utilities.uiutils.ScreenTopBar
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
    LaunchedEffect(Unit) {
        vm.setIdAndGetInfo(teamId)
    }
    Scaffold (
        topBar = {
            ScreenTopBar(
                leftContent = {
                    GoBackButton(navigator)
                },
                title = "Edit Team",
                rightContent = {
                    DeleteButton (
                        delFunction = {
                            vm.deleteTeam()
                            navigator.popBackStack()
                        }
                    )
                }
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                horizontalArrangement = Arrangement.SpaceAround,

                ) {
                Button(
                    onClick = {
                        vm.updateTeam()
                        navigator.popBackStack()
                    },
                    enabled = !vm.teamNameError.collectAsState().value,
                ) {
                    Text("Update Team")
                }
            }
        },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        TeamInputFields(vm, navigator, innerPadding, teamId)
    }
}
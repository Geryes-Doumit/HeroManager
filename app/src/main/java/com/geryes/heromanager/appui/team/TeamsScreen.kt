package com.geryes.heromanager.appui.team

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.geryes.heromanager.R
import com.geryes.heromanager.model.TeamAndPower
import com.geryes.heromanager.model.TeamState
import com.geryes.heromanager.utilities.uiutils.CustomFloatingButton
import com.geryes.heromanager.utilities.uiutils.PowerIconAndValue
import com.geryes.heromanager.utilities.uiutils.ScreenTopBar
import com.geryes.heromanager.utilities.uiutils.StateScreen
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.CreateTeamScreenDestination
import com.ramcosta.composedestinations.generated.destinations.EditTeamScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@Composable
fun TeamsScreen(
    vm: TeamsScreenViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            ScreenTopBar(
                title = stringResource(R.string.teams_screen_title),
            )
        },
        floatingActionButton = {
            CustomFloatingButton {
                navigator.navigate(CreateTeamScreenDestination)
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            StateScreen(
                state = uiState
            ) { content ->
                SuccessTeamsScreen(content, navigator)
            }
        }
    }
}

@Composable
fun SuccessTeamsScreen(
    uiState: TeamsScreenViewModel.UIState,
    navigator: DestinationsNavigator
) {
    LazyColumn {
        items(
            items = uiState.teams,
            key = { teamAndPower -> teamAndPower.id }
        ) { teamAndPower ->
            TeamItem(teamAndPower = teamAndPower, navigator = navigator)
            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f),
            )
        }
    }
}

@Composable
fun TeamItem(teamAndPower: TeamAndPower, navigator: DestinationsNavigator) {
    ListItem(
        headlineContent = {
            Text(
                text = teamAndPower.name
            )
        },
        supportingContent = {
            Text(
                text = getTeamState(teamAndPower.state)
            )
        },
        trailingContent = {
            PowerIconAndValue(
                power = teamAndPower.totalPower,
            )
        },
        modifier = Modifier.clickable {
            navigator.navigate(EditTeamScreenDestination(teamAndPower.id))
        }
    )
}

@Composable
fun getTeamState(enum : TeamState) : String {
    return when (enum) {
        TeamState.BUSY -> stringResource(R.string.busy)
        TeamState.AVAILABLE -> stringResource(R.string.available)
    }
}

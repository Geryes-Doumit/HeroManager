package com.geryes.heromanager.appui.team

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.geryes.heromanager.R
import com.geryes.heromanager.database.TeamDao
import com.geryes.heromanager.model.Team
import com.geryes.heromanager.utilities.uiutils.ScreenTopBar
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TeamPickerViewModel @Inject constructor(
    teamDao: TeamDao
) : ViewModel() {
    val teams = teamDao.getAllTeams()
}

// Used as a dialog in EditHeroScreen

@Composable
fun TeamPicker(
    teamPickerVM : TeamPickerViewModel = hiltViewModel(),
    currentTeam: Team? = null,
    onTeamSelected: (Team?) -> Unit
) {
    val teams = teamPickerVM.teams.collectAsStateWithLifecycle(initialValue = emptyList())
    Dialog(
        onDismissRequest = {
            onTeamSelected(currentTeam)
        },
    ) {
        Scaffold(
            topBar = {
                ScreenTopBar(
                    topMultiplier = 1.2f,
                    title = "Select a team",
                )
            },
            modifier = Modifier.size(400.dp, 500.dp)
                .clip(shape = RoundedCornerShape(23.dp)),
            bottomBar = {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceAround,

                    ) {
                    Button(
                        onClick = {
                            onTeamSelected(null)
                        },
                    ) {
                        Text("Remove Team")
                    }
                }
            }
        ) { innerPadding ->
            LazyColumn (
                modifier = Modifier.padding(innerPadding)
                    .fillMaxWidth(),
            ) {
                items(
                    items = teams.value,
                    key = { team -> team.id }
                ) {
                    item ->
                    TeamPickerItem(
                        team = item,
                        currentTeam = currentTeam,
                        onTeamSelected = onTeamSelected
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun TeamPickerItem(
    team: Team,
    currentTeam: Team? = null,
    onTeamSelected: (Team?) -> Unit
) {
    ListItem(
        headlineContent = {
            var current = ""
            if (team.id == currentTeam?.id) {
                current = " (current)"
            }
            Text(
                text = team.name + current
            )
        },
        supportingContent = {
            Text(
                text = getTeamState(team.state)
            )
        },
        trailingContent = {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.bicep_black),
                    contentDescription = "power",
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "${team.totalPower}",
                )
            }
        },
        modifier = Modifier.clickable {
            onTeamSelected(team)
        }
    )
}
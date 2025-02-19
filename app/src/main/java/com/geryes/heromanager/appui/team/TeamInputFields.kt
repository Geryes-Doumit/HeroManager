package com.geryes.heromanager.appui.team

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.geryes.heromanager.R
import com.geryes.heromanager.appui.hero.HeroPicker
import com.geryes.heromanager.model.Hero
import com.geryes.heromanager.model.TeamState
import com.geryes.heromanager.utilities.uiutils.ScreenTopBar
import com.geryes.heromanager.utilities.uiutils.showToast
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun TeamInputFields(
    vm: TeamViewModel,
    navigator: DestinationsNavigator,
    innerPadding: PaddingValues,
    teamId: Long? = null,
) {
    val teamState = vm.initialTeam.collectAsState().value?.team?.state
    val showLeaderPicker: MutableState<Boolean> = remember { mutableStateOf(false) }
    val showMemberPicker: MutableState<Boolean> = remember { mutableStateOf(false) }

    if (showLeaderPicker.value) {
        HeroPicker(
            dialogTitle = stringResource(R.string.select_leader),
            buttonTitle = stringResource(R.string.remove_leader),
            currentHeroes = listOf(vm.leader.collectAsState().value),
            memberList = vm.members,
            onHeroSelected = {
                showLeaderPicker.value = false
                vm.leader.value = it
                vm.checkDataIsDifferent()
            },
            teamId = teamId
        )
    }

    if (showMemberPicker.value) {
        HeroPicker(
            dialogTitle = stringResource(R.string.select_member),
            currentHeroes = vm.members,
            onHeroSelected = {
                showMemberPicker.value = false
                if (it != null && !vm.members.contains(it)) {
                    vm.members.add(it)
                }
                vm.checkDataIsDifferent()
            },
            teamId = teamId
        )
    }

    // for the toast
    val context = LocalContext.current
    val cannotDeleteTeamToastMsg = stringResource(R.string.cannot_delete_or_edit_team)
    val addMembersBeforeLeaderMsg = stringResource(R.string.add_members_before_leader)

    Column(
        modifier = Modifier.padding(innerPadding)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            //TODO: Implement image selection
        }
        Text(
            text = stringResource(R.string.team_input_title_name),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 10.dp, start = 10.dp),
        )
        OutlinedTextField(
            value = vm.teamName.collectAsState().value,
            onValueChange = {
                vm.teamName.value = it
                vm.checkTeamNameError()
                vm.checkDataIsDifferent()
            },
            label = { Text(stringResource(R.string.team_input_name)) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(23.dp),
            singleLine = true,
            isError = vm.teamNameError.collectAsState().value,
            readOnly = teamState == TeamState.BUSY,
        )
        Text(
            text = stringResource(R.string.team_input_leader),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 10.dp, start = 10.dp),
        )
        OutlinedTextField(
            value = vm.leader.collectAsState().value?.heroName ?: "",
            onValueChange = { },
            label = { Text(stringResource(R.string.team_input_leader)) },
            shape = RoundedCornerShape(23.dp),
            singleLine = true,
            enabled = false,
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = MaterialTheme.colorScheme.onSurface
            ),
            modifier = Modifier.fillMaxWidth()
                .clickable (
                    onClick = {
                        if (vm.members.isEmpty()) {
                            showToast(
                                context,
                                addMembersBeforeLeaderMsg
                            )
                            return@clickable
                        }

                        if (teamState != TeamState.BUSY)
                            showLeaderPicker.value = true
                        else
                            showToast(
                                context,
                                cannotDeleteTeamToastMsg
                            )
                    }
                ),
        )
        Text(
            text = stringResource(R.string.team_input_members),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(
                top = 10.dp,
                start = 10.dp
            ),
        )
        // List of team members + total Power
        Scaffold (
            topBar = {
                ScreenTopBar(
                    topMultiplier = 1.1f,
                    leftContent = {
                        Text(
                            text = stringResource(R.string.team_total_power)
                                    +" ${vm.members.sumOf { it.power }}",
                        )
                    },
                    title = "",
                    rightContent = {
                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "add member",
                                modifier = Modifier.clickable {
                                    if (teamState != TeamState.BUSY)
                                        showMemberPicker.value = true
                                    else
                                        showToast(
                                            context,
                                            cannotDeleteTeamToastMsg
                                        )
                                }
                            )
                        }
                    }
                )
            },
            modifier = Modifier.clip(RoundedCornerShape(18.dp))
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(18.dp),
                ),
        ) { innerPadding ->
            LazyColumn (
                modifier = Modifier.padding(innerPadding),
            ) {
                items(
                    items = vm.members,
                    key = { hero -> hero.id }
                ) {
                    TeamMemberItem(
                        hero = it,
                        onRemove = {
                            if (teamState == TeamState.BUSY)
                                showToast(
                                    context,
                                    cannotDeleteTeamToastMsg
                                )
                            else {
                                vm.members.remove(it)
                                if (vm.leader.value == it) {
                                    vm.leader.value = null
                                }
                                vm.checkDataIsDifferent()
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun TeamMemberItem(hero: Hero, onRemove: () -> Unit) {
    ListItem(
        headlineContent = {
            Text(
                text = hero.heroName
            )
        },
        supportingContent = {
            Text(
                text = hero.realName
            )
        },
        trailingContent = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.bicep_black),
                        contentDescription = stringResource(R.string.power_icon_content_description),
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "${hero.power}",
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "delete",
                        modifier = Modifier.clickable {
                            onRemove()
                        }
                            .padding(start = 20.dp)
                    )
                }
            }
        },
    )
}

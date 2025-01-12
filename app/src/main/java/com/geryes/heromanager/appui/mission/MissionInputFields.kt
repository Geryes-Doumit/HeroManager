package com.geryes.heromanager.appui.mission

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.geryes.heromanager.R
import com.geryes.heromanager.appui.team.TeamPicker
import com.geryes.heromanager.appui.team.getTeamState
import com.geryes.heromanager.model.MissionState
import com.geryes.heromanager.model.TeamState
import com.geryes.heromanager.utilities.uiutils.PowerIconAndValue
import com.geryes.heromanager.utilities.uiutils.showToast
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun MissionInputFields(
    vm: MissionViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    innerPadding: PaddingValues,
) {
    val showTeamPicker : MutableState<Boolean> = remember { mutableStateOf(false) }

    if (showTeamPicker.value) {
        TeamPicker(
            currentTeam = vm.team.collectAsState().value,
            onTeamSelected = {
                showTeamPicker.value = false
                vm.team.value = it
                vm.checkDataIsDifferent()
            }
        )
    }

    Column(
        modifier = Modifier.padding(innerPadding)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            //TODO: Implement image selection
        }
        Text(
            text = stringResource(R.string.mission_input_mission_info),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 10.dp, start = 10.dp),
        )
        OutlinedTextField(
            value = vm.missionName.collectAsState().value,
            onValueChange = {
                vm.missionName.value = it
                vm.checkMissionNameError()
                vm.checkDataIsDifferent()
            },
            label = { Text(stringResource(R.string.mission_name_input)) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(23.dp),
            singleLine = true,
            isError = vm.missionNameError.collectAsState().value,
            readOnly = vm.missionState.collectAsState().value != MissionState.PLANNED,
        )
        OutlinedTextField(
            value = vm.description.collectAsState().value,
            onValueChange = {
                vm.description.value = it
                vm.checkDescriptionError()
                vm.checkDataIsDifferent()
            },
            label = { Text(stringResource(R.string.mission_description_input)) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(23.dp),
            singleLine = false,
            isError = vm.descriptionError.collectAsState().value,
            readOnly = vm.missionState.collectAsState().value != MissionState.PLANNED,
        )
        OutlinedTextField(
            value = vm.minimumPower.collectAsState().value,
            onValueChange = {
                vm.minimumPower.value = it
                vm.checkMinimumPowerError()
                vm.checkDataIsDifferent()
            },
            label = { Text(stringResource(R.string.minimum_power_input)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(23.dp),
            singleLine = true,
            isError = vm.minimumPowerError.collectAsState().value,
            readOnly = vm.missionState.collectAsState().value != MissionState.PLANNED,
        )
        Text(
            text = stringResource(R.string.mission_input_team),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 10.dp, start = 10.dp),
        )
        OutlinedTextField(
            value = vm.team.collectAsState().value?.name ?: "",
            onValueChange = {},
            label = { Text(stringResource(R.string.mission_input_team)) },
            suffix = {
                Text(
                    text = getTeamState(
                        vm.team.collectAsState().value?.state ?: TeamState.AVAILABLE
                    ),
                )
            },
            trailingIcon = {
                PowerIconAndValue(
                    power = vm.team.collectAsState().value?.totalPower,
                    iconSize = 17,
                    textSize = 11
                )
            },
            shape = RoundedCornerShape(23.dp),
            singleLine = true,
            readOnly = true,
            enabled = false,
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                disabledTrailingIconColor = MaterialTheme.colorScheme.onSurface,
            ),
            modifier = Modifier.fillMaxWidth()
                .clickable (
                    onClick = {
                        if (vm.missionState.value == MissionState.PLANNED)
                            showTeamPicker.value = true
                    }
                ),
        )

        if (vm.getId().collectAsState().value > 0) {
            Text(
                text = stringResource(R.string.mission_input_title_state),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 10.dp, start = 10.dp),
            )
            MissionStateActions(vm)
        }
    }
}

@Composable
fun MissionStateActions(
    vm: MissionViewModel,
) {
    // for the toast
    val context = LocalContext.current
    val powerTooLowMessage = stringResource(R.string.team_power_too_low)
    val teamAlreadyBusyMsg = stringResource(R.string.team_already_busy)

    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(23.dp),
        border = BorderStroke(
            width = 2.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
        )
    ) {
        OutlinedTextField(
            value = getMissionState(vm.missionState.collectAsState().value),
            onValueChange = {},
            label = { Text(stringResource(R.string.mission_input_state)) },
            shape = RoundedCornerShape(23.dp),
            singleLine = true,
            readOnly = true,
            enabled = false,
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = MaterialTheme.colorScheme.onSurface
            ),
            modifier = Modifier.fillMaxWidth()
                .padding(10.dp),
        )
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
                .padding(10.dp),
        ) {
            Button(
                onClick = {
                    if (vm.minimumPower.value.toInt() > (vm.team.value?.totalPower ?: 0))
                        showToast(context, powerTooLowMessage)
                    else if (vm.team.value?.state == TeamState.BUSY)
                        showToast(context, teamAlreadyBusyMsg)
                    else
                        vm.startMission()
                },
                modifier = Modifier.padding(10.dp)
                    .width(150.dp),
                enabled = vm.missionState.collectAsState().value == MissionState.PLANNED
                        && !vm.missionNameError.collectAsState().value
                        && !vm.descriptionError.collectAsState().value
                        && !vm.minimumPowerError.collectAsState().value,
            ) {
                Text(stringResource(R.string.start_mission_button))
            }

            Button(
                onClick = {
                    vm.endMission()
                },
                modifier = Modifier.padding(10.dp)
                    .width(150.dp),
                enabled = vm.missionState.collectAsState().value == MissionState.ONGOING,
            ) {
                Text(stringResource(R.string.end_mission_button))
            }
        }
    }
}
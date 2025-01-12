 package com.geryes.heromanager.appui.hero

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
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
import com.geryes.heromanager.model.TeamAndPower
import com.geryes.heromanager.model.TeamState
import com.geryes.heromanager.utilities.uiutils.showToast
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

 @Composable
fun HeroInputFields(
    vm: HeroViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    innerPadding: PaddingValues,
) {
    val showTeamPicker : MutableState<Boolean> = remember { mutableStateOf(false) }

    if (showTeamPicker.value) {
        TeamPicker(
            currentTeam = TeamAndPower.fromTeam(vm.team.collectAsState().value),
            onTeamSelected = {
                showTeamPicker.value = false
                vm.team.value = it?.getTeam()
                vm.checkDataIsDifferent()
            }
        )
    }

    // for the toast
    val context = LocalContext.current
    val toastMessage = stringResource(R.string.cannot_delete_or_edit_hero)

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
            text = stringResource(R.string.hero_input_personal_info),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 10.dp, start = 10.dp),
        )
        OutlinedTextField(
            value = vm.heroName.collectAsState().value,
            onValueChange = {
                vm.heroName.value = it
                vm.checkHeroNameError()
                vm.checkDataIsDifferent()
            },
            label = { Text(stringResource(R.string.hero_name_input)) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(23.dp),
            singleLine = true,
            isError = vm.heroNameError.collectAsState().value,
            readOnly = vm.team.collectAsState().value?.state == TeamState.BUSY,
        )
        OutlinedTextField(
            value = vm.realName.collectAsState().value,
            onValueChange = {
                vm.realName.value = it
                vm.checkRealNameError()
                vm.checkDataIsDifferent()
            },
            label = { Text(stringResource(R.string.real_name_input)) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(23.dp),
            singleLine = true,
            isError = vm.realNameError.collectAsState().value,
            readOnly = vm.team.collectAsState().value?.state == TeamState.BUSY,
        )
        Text(
            text = stringResource(R.string.hero_input_title_stats),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 10.dp, start = 10.dp),
        )
        OutlinedTextField(
            value = vm.power.collectAsState().value,
            onValueChange = {
                vm.power.value = it
                vm.checkPowerError()
                vm.checkDataIsDifferent()
            },
            label = { Text(stringResource(R.string.power_input)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(23.dp),
            singleLine = true,
            isError = vm.powerError.collectAsState().value,
            readOnly = vm.team.collectAsState().value?.state == TeamState.BUSY,
        )
        Text(
            text = stringResource(R.string.hero_input_title_team),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 10.dp, start = 10.dp),
        )
        OutlinedTextField(
            value = vm.team.value?.name ?: "",
            onValueChange = {},
            label = { Text(stringResource(R.string.hero_team_input)) },
            suffix = {
                Text(
                    text = getTeamState(
                        vm.team.collectAsState().value?.state ?: TeamState.AVAILABLE
                    ),
                )
            },
            // trailingIcon = { }, TODO, show team power
            shape = RoundedCornerShape(23.dp),
            singleLine = true,
            readOnly = true,
            enabled = false,
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = MaterialTheme.colorScheme.onSurface
            ),
            modifier = Modifier.fillMaxWidth()
                .clickable (
                    onClick = {
                        if (vm.team.value?.state == TeamState.BUSY)
                            showToast(context, toastMessage)
                        else
                            showTeamPicker.value = true
                    }
                ),
        )
    }
}
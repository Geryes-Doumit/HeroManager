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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.geryes.heromanager.appui.team.TeamPicker
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
            text = "Personal Information",
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
            label = { Text("Hero Name") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(23.dp),
            singleLine = true,
            isError = vm.heroNameError.collectAsState().value,
        )
        OutlinedTextField(
            value = vm.realName.collectAsState().value,
            onValueChange = {
                vm.realName.value = it
                vm.checkRealNameError()
                vm.checkDataIsDifferent()
            },
            label = { Text("Real Name") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(23.dp),
            singleLine = true,
            isError = vm.realNameError.collectAsState().value,
        )
        Text(
            text = "Stats",
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
            label = { Text("Power") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(23.dp),
            singleLine = true,
            isError = vm.powerError.collectAsState().value,
        )
        Text(
            text = "Team",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 10.dp, start = 10.dp),
        )
        OutlinedTextField(
            value = vm.team.value?.name ?: "",
            onValueChange = {},
            label = { Text("Team") },
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
                        showTeamPicker.value = true
                    }
                ),
        )
    }
}
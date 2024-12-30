package com.geryes.heromanager.appui.hero

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.geryes.heromanager.model.Hero
import com.geryes.heromanager.utilities.uiutils.ScreenTopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@Composable
fun CreateHeroScreen(
    vm: CreateHeroScreenViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    var heroName by remember { mutableStateOf("") }
    var heroNameError by remember { mutableStateOf(true) }

    var realName by remember { mutableStateOf("") }
    var realNameError by remember { mutableStateOf(true) }

    var power by remember { mutableStateOf("0") }
    var powerError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            ScreenTopBar(
                leftContent = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Add a Hero",
                        modifier = Modifier.width(30.dp)
                            .padding(end = 8.dp)
                            .clickable {
                                navigator.popBackStack()
                            },
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                },
                title = "Add a Hero",
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                horizontalArrangement = Arrangement.SpaceAround,

            ) {
                Button(
                    onClick = {
                        vm.createHero(
                            Hero(0, heroName, realName, power.toInt(), null)
                        )
                        navigator.popBackStack()
                    },
                    enabled = !heroNameError && !realNameError && !powerError,
                ) {
                    Text("Create Hero")
                }
            }
        },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
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
                value = heroName,
                onValueChange = {
                    heroName = it
                    heroNameError = vm.checkHeroNameError(it)
                },
                label = { Text("Hero Name") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(23.dp),
                singleLine = true,
                isError = heroNameError,
            )
            OutlinedTextField(
                value = realName,
                onValueChange = {
                    realName = it
                    realNameError = vm.checkRealNameError(it)
                },
                label = { Text("Real Name") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(23.dp),
                singleLine = true,
                isError = realNameError,
            )
            Text(
                text = "Stats",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 10.dp, start = 10.dp),
            )
            OutlinedTextField(
                value = power,
                onValueChange = {
                    power = it
                    powerError = vm.checkPowerError(it)
                },
                label = { Text("Power") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(23.dp),
                singleLine = true,
                isError = powerError,
            )
            Text(
                text = "Team",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 10.dp, start = 10.dp),
            )
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Team (not implemented)") },
                shape = RoundedCornerShape(23.dp),
                singleLine = true,
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
                    .clickable (
                        onClick = {
                            //TODO: Implement team selection
                        }
                    ),
            )
        }
    }
}
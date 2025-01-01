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
import androidx.compose.runtime.collectAsState
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
import com.geryes.heromanager.utilities.uiutils.GoBackButton
import com.geryes.heromanager.utilities.uiutils.ScreenTopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@Composable
fun CreateHeroScreen(
    vm: HeroViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    Scaffold(
        topBar = {
            ScreenTopBar(
                leftContent = {
                    GoBackButton(navigator)
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
                        vm.createHero()
                        navigator.popBackStack()
                    },
                    enabled = !vm.heroNameError.collectAsState().value
                            && !vm.realNameError.collectAsState().value
                            && !vm.powerError.collectAsState().value,
                ) {
                    Text("Create Hero")
                }
            }
        },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        HeroInputFields(vm, navigator, innerPadding)
    }
}
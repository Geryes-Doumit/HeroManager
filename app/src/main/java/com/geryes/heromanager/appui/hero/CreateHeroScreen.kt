package com.geryes.heromanager.appui.hero

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.geryes.heromanager.R
import com.geryes.heromanager.utilities.uiutils.AbandonDialog
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
    var showAbandonDialog by remember { mutableStateOf(false) }

    if (showAbandonDialog) {
        AbandonDialog(
            onConfirm = {
                showAbandonDialog = false
                navigator.popBackStack()
            },
            onDismiss = { showAbandonDialog = false }
        )
    }

    BackHandler {
        showAbandonDialog = true
    }

    Scaffold(
        topBar = {
            ScreenTopBar(
                leftContent = {
                    GoBackButton(navigator) {
                        showAbandonDialog = true
                    }
                },
                title = stringResource(R.string.create_hero_title),
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
                    Text(stringResource(R.string.create_hero_button))
                }
            }
        },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        HeroInputFields(vm, navigator, innerPadding)
    }
}
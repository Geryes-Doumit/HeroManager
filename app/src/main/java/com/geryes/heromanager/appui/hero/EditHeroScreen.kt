package com.geryes.heromanager.appui.hero

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.geryes.heromanager.R
import com.geryes.heromanager.model.TeamState
import com.geryes.heromanager.utilities.uiutils.DeleteButton
import com.geryes.heromanager.utilities.uiutils.DeleteDialog
import com.geryes.heromanager.utilities.uiutils.GoBackButton
import com.geryes.heromanager.utilities.uiutils.ScreenTopBar
import com.geryes.heromanager.utilities.uiutils.showToast
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@Composable
fun EditHeroScreen(
    vm: HeroViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    heroId: Long,
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        vm.setIdAndGetInfo(heroId)
    }

    if (showDeleteDialog) {
        DeleteDialog(
            onConfirm = {
                showDeleteDialog = false
                vm.deleteHero()
                navigator.popBackStack()
            },
            onDismiss = { showDeleteDialog = false }
        )
    }

    // for the toast
    val context = LocalContext.current
    val toastMessage = stringResource(R.string.cannot_delete_or_edit_hero)

    Scaffold (
        topBar = {
            ScreenTopBar(
                leftContent = {
                    GoBackButton(navigator)
                },
                title = stringResource(R.string.edit_hero_title),
                rightContent = {
                    DeleteButton (
                        delFunction = {
                            if (vm.team.value?.state == TeamState.BUSY)
                                showToast(
                                    context,
                                    toastMessage
                                )
                            else
                                showDeleteDialog = true
                        }
                    )
                }
            )
        },
        bottomBar = {
            if (vm.team.collectAsState().value?.state == TeamState.AVAILABLE) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceAround,

                    ) {
                    Button(
                        onClick = {
                            vm.updateHero()
                            navigator.popBackStack()
                        },
                        enabled = !vm.heroNameError.collectAsState().value
                                && !vm.realNameError.collectAsState().value
                                && !vm.powerError.collectAsState().value
                                && vm.dataIsDifferent.collectAsState().value,
                    ) {
                        Text(stringResource(R.string.edit_hero_button))
                    }
                }
            }
        },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        HeroInputFields(vm, navigator, innerPadding)
    }
}
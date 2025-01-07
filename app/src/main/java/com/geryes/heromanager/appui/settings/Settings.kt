package com.geryes.heromanager.appui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.geryes.heromanager.R
import com.geryes.heromanager.utilities.uiutils.ScreenTopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph

@Destination<RootGraph>
@Composable
fun SettingsScreen(
    vm: SettingsViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            ScreenTopBar(
            title = stringResource(R.string.settings_screen_title)
            )
        },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Column (
            modifier = Modifier.padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Button (
                onClick = {
                    vm.onFill()
                },
                modifier = Modifier.width(200.dp)
            ) {
                Text(text = stringResource(R.string.fill_button))
            }

            Button(
                onClick = {
                    vm.onClear()
                },
                modifier = Modifier.width(200.dp)
            ) {
                Text(text = stringResource(R.string.clear_button))
            }
        }

    }
}
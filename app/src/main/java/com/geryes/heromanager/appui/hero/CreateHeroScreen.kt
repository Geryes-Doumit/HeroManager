package com.geryes.heromanager.appui.hero

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.geryes.heromanager.utilities.uiutils.ScreenTopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph

@Destination<RootGraph>
@Composable
fun CreateHeroScreen() {
    Scaffold(
        topBar = {
            ScreenTopBar(
                title = "Add a Hero",
            )
        },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ){
            Text("Create Hero Screen")
        }
    }
}
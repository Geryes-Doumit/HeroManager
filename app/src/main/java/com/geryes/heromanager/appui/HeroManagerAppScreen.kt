package com.geryes.heromanager.appui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.geryes.heromanager.R
import com.geryes.heromanager.utilities.animations.NavAppAnimations
import com.geryes.heromanager.utilities.uiutils.AppBottomBar
import com.geryes.heromanager.utilities.uiutils.BottomBarDestination
import com.geryes.heromanager.utilities.uiutils.IconPicture
import com.geryes.heromanager.utilities.uiutils.IconRender
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.NavHostAnimatedDestinationStyle
import com.ramcosta.composedestinations.generated.NavGraphs
import com.ramcosta.composedestinations.generated.destinations.HeroesScreenDestination
import com.ramcosta.composedestinations.generated.destinations.MissionsScreenDestination
import com.ramcosta.composedestinations.generated.destinations.SettingsScreenDestination
import com.ramcosta.composedestinations.generated.destinations.TeamsScreenDestination
import com.ramcosta.composedestinations.spec.DestinationStyle

@Composable
fun HeroManagerAppScreen() {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            Text(
                text = "",
                modifier = Modifier.padding(0.dp)
                    .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f))
                    .fillMaxWidth(),
            )
        },
        bottomBar = {
            AppBottomBar(
                navController,
                NavGraphs.root,
                destinations,
            )
        }
    ) { innerPadding ->
        DestinationsNavHost(
            navGraph = NavGraphs.root,
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            defaultTransitions = NavAppAnimations(),
        )
    }
}

private val destinations = arrayOf<BottomBarDestination>(
    BottomBarDestination(
        direction = HeroesScreenDestination,
        icon = IconRender(
            focused = IconPicture(
                vector = Icons.Filled.Person
            ),
            unfocused = IconPicture(
                vector = Icons.Outlined.Person
            ),
        ),
        labelId = R.string.heroes,
    ),
    BottomBarDestination(
        direction = TeamsScreenDestination,
        icon = IconRender(
            focused = IconPicture(
                vector = Icons.Filled.AccountBox
            ),
            unfocused = IconPicture(
                vector = Icons.Outlined.AccountBox
            ),
        ),
        labelId = R.string.heroTeams,
    ),
    BottomBarDestination(
        direction = MissionsScreenDestination,
        icon = IconRender(
            focused = IconPicture(
                vector = Icons.Filled.CheckCircle
            ),
            unfocused = IconPicture(
                vector = Icons.Outlined.CheckCircle
            ),
        ),
        labelId = R.string.missions,
    ),
    BottomBarDestination(
        direction = SettingsScreenDestination,
        icon = IconRender(
            focused = IconPicture(
                vector = Icons.Filled.Settings
            ),
            unfocused = IconPicture(
                vector = Icons.Outlined.Settings
            ),
        ),
        labelId = R.string.settings,
    )
)

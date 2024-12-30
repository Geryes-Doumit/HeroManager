package com.geryes.heromanager.utilities.animations

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavBackStackEntry
import com.ramcosta.composedestinations.animations.NavHostAnimatedDestinationStyle
import com.ramcosta.composedestinations.generated.destinations.HeroesScreenDestination
import com.ramcosta.composedestinations.generated.destinations.MissionsScreenDestination
import com.ramcosta.composedestinations.generated.destinations.SettingsScreenDestination
import com.ramcosta.composedestinations.generated.destinations.TeamsScreenDestination
import com.ramcosta.composedestinations.utils.destination

class NavAppAnimations : NavHostAnimatedDestinationStyle() {
    override val enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition
        get() = {
            when (targetState.destination()) {
                HeroesScreenDestination -> fadeIn()
                TeamsScreenDestination -> fadeIn()
                MissionsScreenDestination -> fadeIn()
                SettingsScreenDestination -> fadeIn()

                else -> slideInHorizontally() + fadeIn()
            }

        }
    override val exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition
        get() = {
            when (initialState.destination()) {
                HeroesScreenDestination -> fadeOut()
                TeamsScreenDestination -> fadeOut()
                MissionsScreenDestination -> fadeOut()
                SettingsScreenDestination -> fadeOut()

                else -> slideOutHorizontally() + fadeOut()
            }
        }
}
package com.geryes.heromanager.utilities.uiutils

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.ramcosta.composedestinations.utils.isRouteOnBackStackAsState
import com.ramcosta.composedestinations.utils.rememberDestinationsNavigator

class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    val icon: IconRender,
    @StringRes val labelId: Int,
)

@Composable
fun AppBottomBar(
    navController: NavHostController,
    root: NavGraphSpec,
    destinations : Array<BottomBarDestination>,
    saveLastScreen : Boolean = false,
) {
    val navigator = navController.rememberDestinationsNavigator()
    NavigationBar {
        destinations.forEach { destination ->
            val isOnBackStack = navController.isRouteOnBackStackAsState(destination.direction).value
            NavigationBarItem(
                selected = isOnBackStack,
                alwaysShowLabel = false,
                icon = {
                    Icon(
                        selected = isOnBackStack,
                        picture = destination.icon
                    )
                },
                label = {
                    Text(text = stringResource(id = destination.labelId))
                },
                onClick = {
                    if (isOnBackStack) {
                        // When we click again on a bottom bar item and it was already selected
                        // we want to pop the back stack until the initial destination of this bottom bar item
                        navigator.popBackStack(destination.direction, false)
                        return@NavigationBarItem
                    }

                    navigator.navigate(destination.direction) {
                        // Pop up to the root of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(root) {
                            if (saveLastScreen) {
                                saveState = true
                            }
                        }
                        // Avoid multiple copies of the same destination when
                        // select again the same item
                        launchSingleTop = true
                        // Restore state when select again a previously selected item
                        if (saveLastScreen) {
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}
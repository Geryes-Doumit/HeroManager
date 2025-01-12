package com.geryes.heromanager.appui.hero

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.geryes.heromanager.R
import com.geryes.heromanager.model.Hero
import com.geryes.heromanager.utilities.uiutils.CustomFloatingButton
import com.geryes.heromanager.utilities.uiutils.ScreenTopBar
import com.geryes.heromanager.utilities.uiutils.StateScreen
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.CreateHeroScreenDestination
import com.ramcosta.composedestinations.generated.destinations.EditHeroScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>(start = true)
@Composable
fun HeroesScreen(
    vm : HeroesScreenViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            ScreenTopBar(
                title = stringResource(R.string.heroes_screen_title),
            )
        },
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            CustomFloatingButton {
                navigator.navigate(CreateHeroScreenDestination)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ){
            StateScreen(state = uiState) { content ->
                SuccessHeroList(content, navigator)
            }
        }
    }
}

@Composable
fun SuccessHeroList(
    uiState : HeroesScreenViewModel.UIState,
    navigator: DestinationsNavigator
) {
    LazyColumn {
        items(
            items = uiState.heroes,
            key = { hero -> hero.id }
        ) { hero ->
            HeroItem(hero = hero, navigator = navigator)
            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f),
            )
        }
    }
}

@Composable
fun HeroItem(
    hero : Hero,
    navigator: DestinationsNavigator
) {
    ListItem(
        headlineContent = {
            Text(
                text = hero.heroName,
            )
        },
        supportingContent = {
            Text(
                text = hero.realName,
            )
        },
        trailingContent = {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.bicep_black),
                    contentDescription = stringResource(R.string.power_icon_content_description),
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "${hero.power}",
                )
            }
        },
        modifier = Modifier.clickable {
            navigator.navigate(EditHeroScreenDestination(hero.id))
        },
    )
}
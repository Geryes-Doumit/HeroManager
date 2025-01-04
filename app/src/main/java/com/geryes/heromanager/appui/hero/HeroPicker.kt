package com.geryes.heromanager.appui.hero

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.geryes.heromanager.R
import com.geryes.heromanager.model.Hero
import com.geryes.heromanager.repository.HeroRepository
import com.geryes.heromanager.utilities.uiutils.ScreenTopBar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// Used to display a list of heroes to pick from (used in the team edit and create screens)

@HiltViewModel
class HeroPickerViewModel @Inject constructor(
    private val heroRepository: HeroRepository,
) : ViewModel() {
    private var membersToShow: List<Hero>? = null
    private var currentTeamId: Long? = null
    val heroes : MutableStateFlow<List<Hero>> = MutableStateFlow(emptyList())

    fun initInfo(
        memberList: List<Hero>?,
        currentHeroes: List<Hero?>,
        teamId: Long? = null,
    ) = viewModelScope.launch {
        currentTeamId = teamId
        membersToShow = memberList
        initHeroesList(currentHeroes)
    }

    private fun initHeroesList(currentHeroes: List<Hero?>) = viewModelScope.launch {
        if (membersToShow == null) {
            heroRepository.getAllFreeHeroes(currentTeamId)
                .flowOn(Dispatchers.IO).collect { heroList ->
                    heroes.update {
                        heroList.filter {
                            hero -> !currentHeroes.contains(hero)
                        }
                    }
                }
        }
        else {
            heroes.update { membersToShow!! }
        }
    }
}


@Composable
fun HeroPicker(
    heroPickerVM : HeroPickerViewModel = hiltViewModel(),
    dialogTitle: String = "Select a Hero",
    buttonTitle: String = "Cancel",
    currentHeroes: List<Hero?> = emptyList(),
    memberList: List<Hero>? = null,
    onHeroSelected: (Hero?) -> Unit,
    teamId: Long?,
) {
    LaunchedEffect(Unit) {
        heroPickerVM.initInfo(memberList, currentHeroes, teamId)
    }

    val heroes = heroPickerVM.heroes.collectAsStateWithLifecycle(initialValue = emptyList())

    Dialog(
        onDismissRequest = {
            if (currentHeroes.isEmpty()) {
                onHeroSelected(null)
            } else { // to avoid removing the leader when the dialog is dismissed
                onHeroSelected(currentHeroes[0])
            }
        },
    ) {
        Scaffold(
            topBar = {
                ScreenTopBar(
                    topMultiplier = 1.2f,
                    title = dialogTitle,
                )
            },
            modifier = Modifier.size(400.dp, 500.dp)
                .clip(shape = RoundedCornerShape(23.dp)),
            bottomBar = {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceAround,

                    ) {
                    Button(
                        onClick = {
                            onHeroSelected(null)
                        },
                    ) {
                        Text(buttonTitle)
                    }
                }
            }
        ) { innerPadding ->
            LazyColumn (
                modifier = Modifier.padding(innerPadding)
                    .fillMaxWidth(),
            ) {
                item {
                    Column (
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        val text: String = if (memberList != null) "Only showing team members."
                                      else "Only showing heroes that aren't already in a team."
                        Text(
                            text = text,
                            style = TextStyle(
                                fontStyle = FontStyle.Italic,
                                color = androidx.compose.ui.graphics.Color.Gray,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.padding(8.dp).fillMaxWidth(),
                        )
                        HorizontalDivider()
                    }
                }
                items(
                    items = heroes.value,
                    key = { hero -> hero.id }
                ) { item ->
                    val leaderId = if (currentHeroes.isNotEmpty()) currentHeroes[0]?.id else null
                    HeroPickerItem(
                        hero = item,
                        leaderId = leaderId,
                        onHeroSelected = onHeroSelected
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun HeroPickerItem(
    hero: Hero,
    leaderId: Long? = null,
    onHeroSelected: (Hero?) -> Unit,
) {
    ListItem(
        headlineContent = {
            val currentLeader = if (hero.id == leaderId) " (Current leader)" else ""
            Text(
                text = hero.heroName + currentLeader
            )
        },
        supportingContent = {
            Text(
                text = hero.realName
            )
        },
        trailingContent = {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.bicep_black),
                    contentDescription = "power",
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "${hero.power}",
                )
            }
        },
        modifier = Modifier.clickable {
            onHeroSelected(hero)
        }
    )
}
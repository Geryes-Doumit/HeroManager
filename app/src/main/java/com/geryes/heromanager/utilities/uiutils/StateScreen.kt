package com.geryes.heromanager.utilities.uiutils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.geryes.heromanager.R
import com.geryes.heromanager.utilities.vmutils.Result

@Composable
fun LoadingScreen(
    text : String
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = text, modifier = Modifier.padding(vertical = 16.dp))
            // CircularProgressIndicator()
        }
    }
}

@Composable
fun ErrorScreen(
    text : String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(vertical = 16.dp),
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Composable
fun <S> StateScreen (
    state : Result<S>,
    loadingScreen : @Composable (l : Result.Loading) -> Unit = { LoadingScreen(text = stringResource(id = R.string.loading)) },
    errorScreen : @Composable (e : Result.Error) -> Unit = { ErrorScreen(text = stringResource(id = R.string.error)) },
    successScreen : @Composable (state : S) -> Unit,
) {
    when (state) {
        Result.Loading -> loadingScreen(state as Result.Loading)
        is Result.Error -> errorScreen(state)
        is Result.Success -> successScreen (state.content)
    }
}
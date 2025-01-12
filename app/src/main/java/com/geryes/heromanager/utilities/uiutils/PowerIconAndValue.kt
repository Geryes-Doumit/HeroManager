package com.geryes.heromanager.utilities.uiutils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.geryes.heromanager.R

@Composable
fun PowerIconAndValue(
    power: Int?,
    iconSize: Int = 20,
    textSize: Int? = null
) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        androidx.compose.material3.Icon(
            imageVector = ImageVector.vectorResource(R.drawable.bicep_black),
            contentDescription = stringResource(R.string.power_icon_content_description),
            modifier = Modifier.size(iconSize.dp)
        )
        Text(
            text = "${power ?: 0}",
            fontSize = textSize?.sp ?: TextUnit.Unspecified,
        )
    }
}
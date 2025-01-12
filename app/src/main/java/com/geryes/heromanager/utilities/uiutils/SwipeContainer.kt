package com.geryes.heromanager.utilities.uiutils

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SwipeContainer(
    modifier: Modifier = Modifier,
    onEdit: (() -> Unit)? = null,
    onDelete: (() -> Unit)? = null,
    allowEdit: (() -> Boolean) = { true },
    allowDelete: (() -> Boolean) = { true },
    content: @Composable RowScope.() -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            when (it) {

                SwipeToDismissBoxValue.StartToEnd -> {
                    if (onEdit != null)
                        onEdit()
                }
                SwipeToDismissBoxValue.EndToStart -> {
                    if (onDelete != null)
                        onDelete()
                }
                SwipeToDismissBoxValue.Settled -> {
                }
            }
            false
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        modifier = modifier,
        enableDismissFromStartToEnd = onEdit != null && allowEdit(),
        enableDismissFromEndToStart = onDelete != null && allowDelete(),
        backgroundContent = {
            val direction = dismissState.dismissDirection
            if (onEdit == null && direction == SwipeToDismissBoxValue.StartToEnd) return@SwipeToDismissBox
            if (onDelete == null && direction == SwipeToDismissBoxValue.EndToStart) return@SwipeToDismissBox
            val color by animateColorAsState(
                targetValue = when (dismissState.targetValue) {
                    SwipeToDismissBoxValue.Settled -> Color.LightGray
                    SwipeToDismissBoxValue.EndToStart -> MaterialTheme.colorScheme.errorContainer
                    SwipeToDismissBoxValue.StartToEnd -> MaterialTheme.colorScheme.secondaryContainer
                }, label = "color"
            )
            val icon = when (direction) {
                SwipeToDismissBoxValue.StartToEnd-> Icons.Default.Edit
                SwipeToDismissBoxValue.EndToStart -> Icons.Default.Delete
                SwipeToDismissBoxValue.Settled -> Icons.AutoMirrored.Filled.ArrowBack
            }
            val scale = animateFloatAsState(
                targetValue = if (dismissState.targetValue == SwipeToDismissBoxValue.Settled) 0.5f else 1.2f,
                label = "scale"
            )
            val alignment = when (direction) {
                SwipeToDismissBoxValue.StartToEnd -> Alignment.CenterStart
                SwipeToDismissBoxValue.EndToStart -> Alignment.CenterEnd
                SwipeToDismissBoxValue.Settled -> Alignment.Center
            }
            Box (
                modifier = Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(start = 12.dp, end = 12.dp),
                contentAlignment = alignment
            ) {
                androidx.compose.material3.Icon (icon, contentDescription = "icon", modifier = Modifier.scale(scale.value))
            }
        },
        content = content
    )
}
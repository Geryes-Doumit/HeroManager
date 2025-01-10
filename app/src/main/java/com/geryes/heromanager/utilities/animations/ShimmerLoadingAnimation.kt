package com.geryes.heromanager.utilities.animations

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LoadingItemsAnimation(
) {

    /*
    Create InfiniteTransition
    which holds child animation like [Transition]
    animations start running as soon as they enter
    the composition and do not stop unless they are removed
    */
    val transition = rememberInfiniteTransition(
        label = "ShimmerTransition"
    )
    val translateAnim by transition.animateFloat(
        /*
        Specify animation positions,
        initial Values 0F means it starts from 0 position
        */
        initialValue = 0.2f,
        targetValue = 0.4f,
        animationSpec = infiniteRepeatable(

            /*
             Tween Animates between values over specified [durationMillis]
            */
            tween(durationMillis = 1100, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        ),
        label = "ShimmerTranslateAnim"
    )

    /*
      Create a gradient using the list of colors
      Use Linear Gradient for animating in any direction according to requirement
      start=specifies the position to start with in cartesian like system Offset(10f,10f) means x(10,0) , y(0,10)
      end= Animate the end position to give the shimmer effect using the transition created above
    */
    val color = Color.LightGray.copy(alpha = translateAnim)

    LoadingItemsList(
        repeat = 20,
        color = color
    )

}

@Composable
fun LoadingItemsList(
    repeat: Int,
    color: Color
) {
    Column(
        modifier = Modifier.padding(top = 8.dp)
    ) {
        for (i in 0..repeat) {
            LoadingItem(color)
            HorizontalDivider()
        }
    }
}

@Composable
fun LoadingItem(
    color: Color
) {
    /*
      Column composable shaped like a rectangle,
      set the [background]'s [brush] with the
      brush receiving from [ShimmerAnimation]
      which will get animated.
      Add few more Composable to test
    */
    ListItem(
        headlineContent = {
            Column {
                Box(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(6.dp))
                        .width(300.dp)
                        .height(18.dp)
                        .background(color)
                )
                Spacer(
                    modifier = Modifier.height(6.dp)
                )
                Box(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(6.dp))
                        .width(200.dp)
                        .height(18.dp)
                        .background(color)
                )
            }
        },
        trailingContent = {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(6.dp))
                        .size(30.dp)
                        .background(color)
                )
            }
        },
    )
}
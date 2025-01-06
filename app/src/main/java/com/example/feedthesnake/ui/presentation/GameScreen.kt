package com.example.feedthesnake.ui.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feedthesnake.R
import com.example.feedthesnake.ui.theme.DarkGreen
import com.example.feedthesnake.ui.theme.LightBlue
import com.example.feedthesnake.ui.theme.Orange
import kotlinx.coroutines.delay
import kotlin.math.abs
import kotlin.random.Random

@Composable
fun GameScreen() {
    var score by remember {
        mutableIntStateOf(0)
    }
    Scaffold(
        containerColor = LightBlue,
        topBar = { GameScreenTopBar(score) },
        content = { innerPadding -> GameScreenContent(modifier = Modifier.padding(innerPadding), onScoreChange = { newScore ->
            score = newScore }) })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreenTopBar(score:Int) {
    TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
        containerColor = LightBlue
    ), title = {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.back_icon),
                    contentDescription = stringResource(R.string.back),
                    tint = Color.Unspecified,
                    modifier = Modifier.size(35.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .padding(top = 5.dp, bottom = 5.dp, end = 20.dp)
                    .background(color = Color(0xFF71D46B), shape = RoundedCornerShape(16.dp))
                    .padding(horizontal = 15.dp, vertical = 5.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.score),
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 2.dp)
                    )
                    Text(
                        text = score.toString(),
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

        }
    }, actions = {

    })
}

@Composable
fun GameScreenContent(modifier: Modifier, onScoreChange: (Int) -> Unit) {
    var snakeBody by remember { mutableStateOf(listOf(Offset(100f, 100f))) }
    var foodPosition by remember { mutableStateOf(randomOffset()) }
    var snakeDirection by remember { mutableStateOf(Offset(1f, 0f)) }
    val blockSize = with(LocalDensity.current) { 20.dp.toPx() }
    var score by remember {
        mutableIntStateOf(0)
    }

    LaunchedEffect(key1 = Unit) {
        while (true) {
            delay(200)
            val newHead = snakeBody.first().copy(
                x = snakeBody.first().x + blockSize * snakeDirection.x,
                y = snakeBody.first().y + blockSize * snakeDirection.y
            )
            snakeBody = listOf(newHead) + snakeBody.dropLast(1)

            if (newHead.x >= foodPosition.x && newHead.x < foodPosition.x + blockSize &&
                newHead.y >= foodPosition.y && newHead.y < foodPosition.y + blockSize
            ) {
                snakeBody = listOf(newHead) + snakeBody
                foodPosition = randomOffset()
                score +=5
                onScoreChange(score)
            }
        }
    }

    Canvas(modifier = modifier
        .fillMaxSize()
        .padding(15.dp)
        .border(3.dp, DarkGreen)
        .pointerInput(Unit) {
            detectDragGestures { change, dragAmount ->
                val (dragX, dragY) = dragAmount
                snakeDirection = when {
                    abs(dragX) > abs(dragY) -> if (dragX > 0) Offset(1f, 0f) else Offset(
                        -1f,
                        0f
                    )

                    else -> if (dragY > 0) Offset(0f, 1f) else Offset(0f, -1f)
                }
            }
        }) {
        snakeBody.forEach { bodyPart ->
            drawCircle(
                color = DarkGreen,
                center = bodyPart + Offset(blockSize / 2, blockSize / 2),
                radius = blockSize / 2
            )
        }

        drawCircle(
            color = Orange,
            center = foodPosition + Offset(blockSize / 2, blockSize / 2),
            radius = blockSize / 2
        )
    }
}

fun randomOffset(): Offset {
    val random = Random.Default
    return Offset(
        x = random.nextInt(from = 0, until = 300).toFloat(),
        y = random.nextInt(from = 0, until = 600).toFloat()
    )
}

@Composable
@Preview
fun GameScreenPrev() {
    GameScreen()
}
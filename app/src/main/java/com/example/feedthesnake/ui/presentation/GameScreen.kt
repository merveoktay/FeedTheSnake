package com.example.feedthesnake.ui.presentation

import android.content.Context
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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.feedthesnake.R
import com.example.feedthesnake.constants.SizeConstants
import com.example.feedthesnake.model.SharedPreferencesHelper
import com.example.feedthesnake.theme.DarkGreen
import com.example.feedthesnake.theme.Green
import com.example.feedthesnake.theme.LightBlue
import com.example.feedthesnake.theme.Orange
import com.example.feedthesnake.viewModel.SnakeViewModel
import kotlinx.coroutines.delay
import kotlin.math.abs
import kotlin.random.Random

@Composable
fun GameScreen(
    name: String,
    onNavigateToHome: () -> Unit,
    onNavigateToGameOver: (Int) -> Unit,
    snakeViewModel: SnakeViewModel
) {
    val context = LocalContext.current
    var score by remember {
        mutableIntStateOf(0)
    }
    Scaffold(
        containerColor = LightBlue,
        topBar = { GameScreenTopBar(score,onNavigateToHome) },
        content = { innerPadding -> GameScreenContent(context=context,name=name,snakeViewModel=snakeViewModel,modifier = Modifier.padding(innerPadding), onScoreChange = { newScore ->
            score = newScore },
            onNavigateToGameOver = onNavigateToGameOver
        ) })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreenTopBar(score:Int,onNavigateToHome: () -> Unit) {
    TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
        containerColor = LightBlue
    ), title = {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { onNavigateToHome() }) {
                Icon(
                    painter = painterResource(id = R.drawable.back_icon),
                    contentDescription = stringResource(R.string.back),
                    tint = Color.Unspecified,
                    modifier = Modifier.size(SizeConstants.ICON_SIZE)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .padding(top = SizeConstants.MIN_PADDING_SIZE, bottom = SizeConstants.MIN_PADDING_SIZE, end = SizeConstants.MEDIUM_PADDING_SIZE)
                    .background(color = Green, shape = RoundedCornerShape(SizeConstants.MIN_CORNER_SHAPE_SIZE))
                    .padding(horizontal =SizeConstants.SMALL_PADDING_SIZE),
                contentAlignment = Alignment.CenterEnd
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.score),
                        color = Color.Black,
                        fontSize = SizeConstants.MIN_FONT_SIZE,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = score.toString(),
                        color = Color.Black,
                        fontSize = SizeConstants.MIN_FONT_SIZE,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

        }
    }, actions = {

    })
}

fun randomOffset(canvasWidth: Float, canvasHeight: Float, blockSize: Float): Offset {
    val random = Random.Default
    return Offset(
        x = random.nextInt(from = 0, until = (canvasWidth / blockSize).toInt()) * blockSize,
        y = random.nextInt(from = 0, until = (canvasHeight / blockSize).toInt()) * blockSize
    )
}


@Composable
fun GameScreenContent(
    name: String,
    snakeViewModel: SnakeViewModel,
    modifier: Modifier,
    onScoreChange: (Int) -> Unit,
    onNavigateToGameOver: (Int) -> Unit,
    context: Context
) {
    var snakeBody by remember { mutableStateOf(listOf(Offset(SizeConstants.MAX_OFFSET_SIZE, SizeConstants.MAX_OFFSET_SIZE))) }
    var foodPosition by remember { mutableStateOf(Offset.Zero) }
    var snakeDirection by remember { mutableStateOf(Offset(SizeConstants.MEDIUM_OFFSET_SIZE, SizeConstants.MIN_OFFSET_SIZE)) }
    val blockSize = with(LocalDensity.current) { SizeConstants.BLOCK_SIZE.toPx() }
    var score by remember { mutableIntStateOf(0) }
    var canvasSize by remember { mutableStateOf(Size.Zero) }
    var isGameOver by remember { mutableStateOf(false) } // Game Over bayrağı

    LaunchedEffect(canvasSize) {
        foodPosition = randomOffset(canvasSize.width, canvasSize.height, blockSize)
    }

    LaunchedEffect(key1 = canvasSize) {
        while (!isGameOver) {
            delay(SharedPreferencesHelper.getDifficulty(context =context))

            val newHead = snakeBody.first().copy(
                x = snakeBody.first().x + blockSize * snakeDirection.x,
                y = snakeBody.first().y + blockSize * snakeDirection.y
            )

            // Canvas sınır kontrolü (duvara çarpma)
            if (newHead.x < 0 || newHead.x + blockSize > canvasSize.width ||
                newHead.y < 0 || newHead.y + blockSize > canvasSize.height
            ) {
                isGameOver = true
                snakeViewModel.saveSnake(name,score)
                onNavigateToGameOver(score)
                return@LaunchedEffect
            }

            // Kendine çarpma kontrolü
            if (snakeBody.drop(SizeConstants.DROP_SIZE).contains(newHead)) {
                isGameOver = true
                snakeViewModel.saveSnake(name,score)
                onNavigateToGameOver(score)
                return@LaunchedEffect
            }

            // Yılanın pozisyonunu güncelle
            snakeBody = listOf(newHead) + snakeBody.dropLast(1)

            // Yem yeme kontrolü
            val headCenter = newHead + Offset(blockSize / SizeConstants.BLOCK_SIZE_DIVIDER, blockSize / SizeConstants.BLOCK_SIZE_DIVIDER)
            val foodCenter = foodPosition + Offset(blockSize / SizeConstants.BLOCK_SIZE_DIVIDER, blockSize / SizeConstants.BLOCK_SIZE_DIVIDER)
            if ((headCenter - foodCenter).getDistance() < blockSize) {
                snakeBody = listOf(newHead) + snakeBody
                foodPosition = randomOffset(canvasSize.width, canvasSize.height, blockSize)
                score +=SizeConstants.SCORE
                onScoreChange(score)
            }
        }
    }

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .padding(SizeConstants.SMALL_PADDING_SIZE)
            .border(SizeConstants.BORDER_SIZE, DarkGreen)
            .pointerInput(Unit) {
                detectDragGestures { _, dragAmount ->
                    val (dragX, dragY) = dragAmount
                    snakeDirection = when {
                        abs(dragX) > abs(dragY) -> if (dragX > 0) Offset(SizeConstants.MEDIUM_OFFSET_SIZE, SizeConstants.MIN_OFFSET_SIZE) else Offset(-SizeConstants.MEDIUM_OFFSET_SIZE, SizeConstants.MIN_OFFSET_SIZE)
                        else -> if (dragY > 0) Offset(SizeConstants.MIN_OFFSET_SIZE, SizeConstants.MEDIUM_OFFSET_SIZE) else Offset(SizeConstants.MIN_OFFSET_SIZE, -SizeConstants.MEDIUM_OFFSET_SIZE)
                    }

                }
            }
    ) {
        canvasSize = size

        snakeBody.forEach { bodyPart ->
            drawCircle(
                color = DarkGreen,
                center = bodyPart + Offset(blockSize / SizeConstants.BLOCK_SIZE_DIVIDER, blockSize / SizeConstants.BLOCK_SIZE_DIVIDER),
                radius = blockSize / SizeConstants.BLOCK_SIZE_DIVIDER
            )
        }

        drawCircle(
            color = Orange,
            center = foodPosition + Offset(blockSize / SizeConstants.BLOCK_SIZE_DIVIDER, blockSize / SizeConstants.BLOCK_SIZE_DIVIDER),
            radius = blockSize / SizeConstants.BLOCK_SIZE_DIVIDER
        )
    }
}



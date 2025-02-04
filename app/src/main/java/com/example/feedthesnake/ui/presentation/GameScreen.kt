package com.example.feedthesnake.ui.presentation

import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.feedthesnake.R
import com.example.feedthesnake.constants.SizeConstants
import com.example.feedthesnake.model.SharedPreferencesHelper
import com.example.feedthesnake.theme.DarkGreen
import com.example.feedthesnake.theme.Green
import com.example.feedthesnake.theme.LightBlue
import com.example.feedthesnake.theme.Orange
import com.example.feedthesnake.viewModel.SnakeViewModel
import kotlinx.coroutines.delay
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
        modifier = Modifier.testTag("GameScreen"),
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
    val snakeBody by snakeViewModel.snakeBody.collectAsState()
    val foodPosition by snakeViewModel.foodPosition.collectAsState()
    val snakeDirection by snakeViewModel.snakeDirection.collectAsState()
    val score by snakeViewModel.score
    val isGameOver by snakeViewModel.isGameOver.collectAsState()

    val blockSize = with(LocalDensity.current) { SizeConstants.BLOCK_SIZE.toPx() }
    var canvasSize by remember { mutableStateOf(Size.Zero) }

    LaunchedEffect(canvasSize) {
        snakeViewModel.initializeFood(canvasSize.width, canvasSize.height, blockSize)
    }

    LaunchedEffect(canvasSize) {
        while (!isGameOver) {
            delay(SharedPreferencesHelper.getDifficulty(context = context))
            snakeViewModel.moveSnake(blockSize, canvasSize, name, onNavigateToGameOver)
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween, // İçeriği dikey olarak yay
        horizontalAlignment = Alignment.CenterHorizontally // İçeriği yatayda ortala
    ) {
        Box(
            modifier = Modifier
                .weight(1f) // Canvas'ın mümkün olduğunca büyük olmasını sağlar
                .fillMaxWidth()
                .padding(SizeConstants.SMALL_PADDING_SIZE)
                .border(SizeConstants.BORDER_SIZE, DarkGreen) // Kenarlık eklendi
        ) {
            Canvas(
                modifier = Modifier.matchParentSize()
            ) {
                canvasSize = size

                // Yılanı çiz
                snakeBody.forEach { bodyPart ->
                    drawCircle(
                        color = DarkGreen,
                        center = bodyPart + Offset(blockSize / 2, blockSize / 2),
                        radius = blockSize / 2
                    )
                }

                // Yemi çiz
                drawCircle(
                    color = Orange,
                    center = foodPosition + Offset(blockSize / 2, blockSize / 2),
                    radius = blockSize / 2
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        ControlPanel(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SizeConstants.SMALL_PADDING_SIZE),
            onDirectionChange = { direction ->
                snakeViewModel.updateDirection(direction.x, direction.y)
            }
        )
    }
}


@Composable
fun ControlPanel(onDirectionChange: (Offset) -> Unit, modifier: Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .size(150.dp)
            .border(2.dp, DarkGreen, RoundedCornerShape(12.dp))
    ) {
        IconButton(onClick = { onDirectionChange(Offset(0f, -1f)) }) {
            Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Up")
        }
        Row {
            IconButton(onClick = { onDirectionChange(Offset(-1f, 0f)) }) {
                Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Left")
            }
            Spacer(modifier = Modifier.width(24.dp))
            IconButton(onClick = { onDirectionChange(Offset(1f, 0f)) }) {
                Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Right")
            }
        }
        IconButton(onClick = { onDirectionChange(Offset(0f, 1f)) }) {
            Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Down")
        }
    }
}


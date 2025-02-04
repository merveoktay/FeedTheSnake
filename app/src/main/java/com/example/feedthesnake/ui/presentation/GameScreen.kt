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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.feedthesnake.R
import com.example.feedthesnake.constants.SizeConstants.BLOCK_SIZE
import com.example.feedthesnake.constants.SizeConstants.BLOCK_SIZE_DIVIDER
import com.example.feedthesnake.constants.SizeConstants.BORDER_SIZE
import com.example.feedthesnake.constants.SizeConstants.COLUMN_SIZE
import com.example.feedthesnake.constants.SizeConstants.ICON_SIZE
import com.example.feedthesnake.constants.SizeConstants.MEDIUM_OFFSET_SIZE
import com.example.feedthesnake.constants.SizeConstants.MEDIUM_PADDING_SIZE
import com.example.feedthesnake.constants.SizeConstants.MIN_CORNER_SHAPE_SIZE
import com.example.feedthesnake.constants.SizeConstants.MIN_FONT_SIZE
import com.example.feedthesnake.constants.SizeConstants.MIN_OFFSET_SIZE
import com.example.feedthesnake.constants.SizeConstants.MIN_PADDING_SIZE
import com.example.feedthesnake.constants.SizeConstants.SMALL_PADDING_SIZE
import com.example.feedthesnake.constants.SizeConstants.WEIGHT_SIZE
import com.example.feedthesnake.constants.SizeConstants.WIDTH_SIZE
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
                    modifier = Modifier.size(ICON_SIZE)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .padding(top = MIN_PADDING_SIZE, bottom = MIN_PADDING_SIZE, end = MEDIUM_PADDING_SIZE)
                    .background(color = Green, shape = RoundedCornerShape(MIN_CORNER_SHAPE_SIZE))
                    .padding(horizontal =SMALL_PADDING_SIZE),
                contentAlignment = Alignment.CenterEnd
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.score),
                        color = Color.Black,
                        fontSize = MIN_FONT_SIZE,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = score.toString(),
                        color = Color.Black,
                        fontSize = MIN_FONT_SIZE,
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

    val blockSize = with(LocalDensity.current) { BLOCK_SIZE.toPx() }
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
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .weight(WEIGHT_SIZE)
                .fillMaxWidth()
                .padding(SMALL_PADDING_SIZE)
                .border(BORDER_SIZE, DarkGreen)
        ) {
            Canvas(
                modifier = Modifier.matchParentSize()
            ) {
                canvasSize = size

                snakeBody.forEach { bodyPart ->
                    drawCircle(
                        color = DarkGreen,
                        center = bodyPart + Offset(blockSize / BLOCK_SIZE_DIVIDER, blockSize / BLOCK_SIZE_DIVIDER),
                        radius = blockSize / BLOCK_SIZE_DIVIDER
                    )
                }

                drawCircle(
                    color = Orange,
                    center = foodPosition + Offset(blockSize / BLOCK_SIZE_DIVIDER, blockSize / BLOCK_SIZE_DIVIDER),
                    radius = blockSize / BLOCK_SIZE_DIVIDER
                )
            }
        }

        ControlPanel(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SMALL_PADDING_SIZE),
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
            .size(COLUMN_SIZE)
            .border(BORDER_SIZE, DarkGreen, RoundedCornerShape(MIN_CORNER_SHAPE_SIZE))
    ) {
        IconButton(onClick = { onDirectionChange(Offset(MIN_OFFSET_SIZE, -MEDIUM_OFFSET_SIZE)) }) {
            Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Up")
        }
        Row {
            IconButton(onClick = { onDirectionChange(Offset(-MEDIUM_OFFSET_SIZE, MIN_OFFSET_SIZE)) }) {
                Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Left")
            }
            Spacer(modifier = Modifier.width(WIDTH_SIZE))
            IconButton(onClick = { onDirectionChange(Offset(MEDIUM_OFFSET_SIZE, MIN_OFFSET_SIZE)) }) {
                Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Right")
            }
        }
        IconButton(onClick = { onDirectionChange(Offset(MIN_OFFSET_SIZE, MEDIUM_OFFSET_SIZE)) }) {
            Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Down")
        }
    }
}


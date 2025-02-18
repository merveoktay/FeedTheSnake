package com.example.feedthesnake.ui.presentation

import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.feedthesnake.constants.SizeConstants.MAX_ICON_SIZE
import com.example.feedthesnake.constants.SizeConstants.MAX_WIDTH_SIZE
import com.example.feedthesnake.constants.SizeConstants.MEDIUM_OFFSET_SIZE
import com.example.feedthesnake.constants.SizeConstants.MEDIUM_PADDING_SIZE
import com.example.feedthesnake.constants.SizeConstants.MIN_CORNER_SHAPE_SIZE
import com.example.feedthesnake.constants.SizeConstants.MIN_FONT_SIZE
import com.example.feedthesnake.constants.SizeConstants.MIN_OFFSET_SIZE
import com.example.feedthesnake.constants.SizeConstants.MIN_PADDING_SIZE
import com.example.feedthesnake.constants.SizeConstants.SMALL_PADDING_SIZE
import com.example.feedthesnake.constants.SizeConstants.WEIGHT_SIZE
import com.example.feedthesnake.model.SharedPreferencesHelper
import com.example.feedthesnake.theme.DarkGreen
import com.example.feedthesnake.theme.Green
import com.example.feedthesnake.theme.LightBlue
import com.example.feedthesnake.theme.LightWaterGreen
import com.example.feedthesnake.theme.Orange
import com.example.feedthesnake.theme.Yellow
import com.example.feedthesnake.util.MusicManager
import com.example.feedthesnake.viewModel.SnakeViewModel
import kotlinx.coroutines.delay

@Composable
fun GameScreen(
    name: String,
    onNavigateToHome: () -> Unit,
    onNavigateToGameOver: (Int) -> Unit,
    snakeViewModel: SnakeViewModel
) {
    val context = LocalContext.current
    val score by snakeViewModel.score.collectAsState()
    Scaffold(
        modifier = Modifier.testTag("GameScreen"),
        containerColor = LightBlue,
        topBar = { GameScreenTopBar(score,onNavigateToHome) },
        content = { innerPadding -> GameScreenContent(
            context = context,
            name = name,
            snakeViewModel = snakeViewModel,
            modifier = Modifier.padding(innerPadding),
            onScoreChange = { newScore -> snakeViewModel.updateScore(newScore) },
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
    val isGameOver by snakeViewModel.isGameOver.collectAsState()

    val blockSize = with(LocalDensity.current) { BLOCK_SIZE.toPx() }
    var canvasSize by remember { mutableStateOf(Size.Zero) }
    LaunchedEffect(Unit) {
        if (MusicManager.isMusicPlay) {
            MusicManager.playMusic(context, R.raw.game_music)
        }
    }
    LaunchedEffect(canvasSize, isGameOver) {
        if (canvasSize.width > 0 && canvasSize.height > 0) {
            snakeViewModel.initializeFood(canvasSize.width, canvasSize.height, blockSize)
        }

        while (!isGameOver) {
            delay(SharedPreferencesHelper.getDifficulty(context))
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
                modifier = Modifier.matchParentSize().background(color = LightWaterGreen)
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
                    color =  listOf(Yellow, Orange, Green).random(),
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
            },
            snakeDirection = snakeDirection
        )
    }
}


@Composable
fun ControlPanel(
    onDirectionChange: (Offset) -> Unit,
    modifier: Modifier,
    snakeDirection: Offset
) {
    val up = Offset(MIN_OFFSET_SIZE, -MEDIUM_OFFSET_SIZE)
    val down = Offset(MIN_OFFSET_SIZE, MEDIUM_OFFSET_SIZE)
    val left = Offset(-MEDIUM_OFFSET_SIZE, MIN_OFFSET_SIZE)
    val right = Offset(MEDIUM_OFFSET_SIZE, MIN_OFFSET_SIZE)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .size(COLUMN_SIZE)

    ) {
        IconButton(
            onClick = {
                if (snakeDirection != down) {
                    onDirectionChange(up)
                }
            }
        ) {
            Icon(modifier = Modifier.size(MAX_ICON_SIZE), tint = Color.Unspecified, painter = painterResource(R.drawable.up_arrow), contentDescription = "Up")
        }
        Row {
            IconButton(
                onClick = {
                    if (snakeDirection != right) {
                        onDirectionChange(left)
                    }
                }
            ) {
                Icon(modifier = Modifier.size(MAX_ICON_SIZE), tint = Color.Unspecified, painter = painterResource(R.drawable.left_arrow), contentDescription = "Left")
            }
            Spacer(modifier = Modifier.width(MAX_WIDTH_SIZE))
            IconButton(
                onClick = {
                    if (snakeDirection != left) {
                        onDirectionChange(right)
                    }
                }
            ) {
                Icon(modifier = Modifier.size(MAX_ICON_SIZE), tint = Color.Unspecified, painter = painterResource(R.drawable.right_arrow), contentDescription = "Right")
            }
        }
        IconButton(
            onClick = {
                if (snakeDirection != up) {
                    onDirectionChange(down)
                }
            }
        ) {
            Icon(modifier = Modifier.size(MAX_ICON_SIZE), tint = Color.Unspecified, painter = painterResource(R.drawable.down_arrow), contentDescription = "Down")
        }
    }
}




package com.example.feedthesnake.ui.presentation

import android.util.Log
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feedthesnake.R
import com.example.feedthesnake.theme.DarkGreen
import com.example.feedthesnake.theme.LightBlue
import com.example.feedthesnake.theme.Orange
import com.example.feedthesnake.viewModel.SnakeViewModel
import kotlinx.coroutines.delay
import kotlin.math.abs
import kotlin.random.Random

@Composable
fun GameScreen(name:String,onNavigateToHome: () -> Unit,onNavigateToGameOver: (Int) -> Unit,snakeViewModel: SnakeViewModel) {
    var score by remember {
        mutableIntStateOf(0)
    }
    Scaffold(
        containerColor = LightBlue,
        topBar = { GameScreenTopBar(score,onNavigateToHome) },
        content = { innerPadding -> GameScreenContent(name=name,snakeViewModel=snakeViewModel,modifier = Modifier.padding(innerPadding), onScoreChange = { newScore ->
            score = newScore },onNavigateToGameOver) })
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
                    modifier = Modifier.size(35.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .padding(top = 5.dp, bottom = 5.dp, end = 20.dp)
                    .background(color = Color(0xFF71D46B), shape = RoundedCornerShape(16.dp))
                    .padding(horizontal = 15.dp),
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
    onNavigateToGameOver: (Int) -> Unit
) {
    var snakeBody by remember { mutableStateOf(listOf(Offset(100f, 100f))) }
    var foodPosition by remember { mutableStateOf(Offset.Zero) }
    var snakeDirection by remember { mutableStateOf(Offset(1f, 0f)) }
    val blockSize = with(LocalDensity.current) { 20.dp.toPx() }
    var score by remember { mutableIntStateOf(0) }
    var canvasSize by remember { mutableStateOf(Size.Zero) }
    var isGameOver by remember { mutableStateOf(false) } // Game Over bayrağı

    LaunchedEffect(canvasSize) {
        foodPosition = randomOffset(canvasSize.width, canvasSize.height, blockSize)
    }

    LaunchedEffect(key1 = canvasSize) {
        while (!isGameOver) { // Eğer oyun bitmişse döngü durdurulacak
            delay(200)

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
                Log.d("Kayıt","$name  -  $score")
                onNavigateToGameOver(score) // Oyun bittiğinde geçiş yap
                return@LaunchedEffect
            }

            // Kendine çarpma kontrolü
            if (snakeBody.drop(1).contains(newHead)) {
                isGameOver = true
                snakeViewModel.saveSnake(name,score)
                Log.d("Kayıt","$name  -  $score")
                onNavigateToGameOver(score) // Oyun bittiğinde geçiş yap
                return@LaunchedEffect
            }

            // Yılanın pozisyonunu güncelle
            snakeBody = listOf(newHead) + snakeBody.dropLast(1)

            // Yem yeme kontrolü
            val headCenter = newHead + Offset(blockSize / 2, blockSize / 2)
            val foodCenter = foodPosition + Offset(blockSize / 2, blockSize / 2)
            if ((headCenter - foodCenter).getDistance() < blockSize) {
                snakeBody = listOf(newHead) + snakeBody
                foodPosition = randomOffset(canvasSize.width, canvasSize.height, blockSize)
                score += 5
                onScoreChange(score)
            }
        }
    }

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .padding(15.dp)
            .border(3.dp, DarkGreen)
            .pointerInput(Unit) {
                detectDragGestures { _, dragAmount ->
                    val (dragX, dragY) = dragAmount
                    snakeDirection = when {
                        abs(dragX) > abs(dragY) -> if (dragX > 0) Offset(1f, 0f) else Offset(-1f, 0f)
                        else -> if (dragY > 0) Offset(0f, 1f) else Offset(0f, -1f)
                    }
                }
            }
    ) {
        canvasSize = size

        // Yılanın gövdesini çiz
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



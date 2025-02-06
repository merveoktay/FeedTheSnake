package com.example.feedthesnake.viewModel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feedthesnake.constants.SizeConstants.BLOCK_SIZE_DIVIDER
import com.example.feedthesnake.constants.SizeConstants.DROP_SIZE
import com.example.feedthesnake.constants.SizeConstants.MAX_OFFSET_SIZE
import com.example.feedthesnake.constants.SizeConstants.MEDIUM_OFFSET_SIZE
import com.example.feedthesnake.constants.SizeConstants.MIN_OFFSET_SIZE
import com.example.feedthesnake.constants.SizeConstants.SCORE
import com.example.feedthesnake.model.Snake
import com.example.feedthesnake.repository.SnakeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
class SnakeViewModel @Inject constructor(private val repository: SnakeRepository) : ViewModel() {

    private val _topSnakes = MutableStateFlow<List<Snake>>(emptyList())
    val topSnakes: StateFlow<List<Snake>> get() = _topSnakes

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score

    private val _snakeBody = MutableStateFlow(listOf(Offset(MAX_OFFSET_SIZE, MAX_OFFSET_SIZE)))
    val snakeBody: StateFlow<List<Offset>> get() = _snakeBody

    private val _foodPosition = MutableStateFlow(Offset(MAX_OFFSET_SIZE, MAX_OFFSET_SIZE))
    val foodPosition: StateFlow<Offset> get() = _foodPosition

    private val _snakeDirection = MutableStateFlow(Offset(MEDIUM_OFFSET_SIZE, MIN_OFFSET_SIZE))
    val snakeDirection: StateFlow<Offset> get() = _snakeDirection

    private val _isGameOver = MutableStateFlow(false)
    val isGameOver: StateFlow<Boolean> get() = _isGameOver

    init {
        fetchTopSnakes()
    }

    fun fetchTopSnakes() {
        viewModelScope.launch {
            try {
                val scores = repository.getTopSnakes()
                _topSnakes.value = scores
            } catch (e: Exception) {
                Log.d("Error",e.message.toString())
            }
        }
    }

    fun updateScore(newScore: Int) {
        _score.value = newScore
    }

    fun saveSnake(name: String, score: Int) {
        viewModelScope.launch {
            val snake = Snake(name = name, score = score)
            repository.insertSnake(snake)
        }
    }

    fun initializeFood(width: Float, height: Float, blockSize: Float) {
        _foodPosition.value = randomOffset(width, height, blockSize)
    }

    fun moveSnake(blockSize: Float, canvasSize: Size, name: String, onGameOver: (Int) -> Unit) {
        val newHead = _snakeBody.value.first().copy(
            x = _snakeBody.value.first().x + blockSize * _snakeDirection.value.x,
            y = _snakeBody.value.first().y + blockSize * _snakeDirection.value.y
        )

        if (newHead.x < 0 || newHead.x + blockSize > canvasSize.width ||
            newHead.y < 0 || newHead.y + blockSize > canvasSize.height ||
            _snakeBody.value.drop(DROP_SIZE).contains(newHead)
        ) {
            _isGameOver.value = true
            saveSnake(name, _score.value)
            onGameOver(_score.value)
            return
        }

        val headCenter = newHead + Offset(blockSize / BLOCK_SIZE_DIVIDER, blockSize / BLOCK_SIZE_DIVIDER)
        val foodCenter = _foodPosition.value + Offset(blockSize / BLOCK_SIZE_DIVIDER, blockSize / BLOCK_SIZE_DIVIDER)
        if ((headCenter - foodCenter).getDistance() < blockSize) {
            _snakeBody.value = listOf(newHead) + _snakeBody.value
            _foodPosition.value = randomOffset(canvasSize.width, canvasSize.height, blockSize)
            _score.value += SCORE
        } else {
            _snakeBody.value = listOf(newHead) + _snakeBody.value.dropLast(DROP_SIZE)
        }
    }

    fun updateDirection(dragX: Float, dragY: Float) {
        _snakeDirection.value = when {
            abs(dragX) > abs(dragY) -> if (dragX > 0) Offset(MEDIUM_OFFSET_SIZE, MIN_OFFSET_SIZE) else Offset(-MEDIUM_OFFSET_SIZE, MIN_OFFSET_SIZE)
            else -> if (dragY > 0) Offset(MIN_OFFSET_SIZE, MEDIUM_OFFSET_SIZE) else Offset(MIN_OFFSET_SIZE, -MEDIUM_OFFSET_SIZE)
        }
    }
    private fun randomOffset(width: Float, height: Float, blockSize: Float): Offset {
        val randomX = (0 until (width / blockSize).toInt()).random() * blockSize
        val randomY = (0 until (height / blockSize).toInt()).random() * blockSize
        return Offset(randomX, randomY)
    }

}

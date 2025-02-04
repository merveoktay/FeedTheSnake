package com.example.feedthesnake.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _score = mutableIntStateOf(0)
    val score: State<Int> = _score

    private val _snakeBody = MutableStateFlow(listOf(Offset(100f, 100f)))
    val snakeBody: StateFlow<List<Offset>> get() = _snakeBody

    private val _foodPosition = MutableStateFlow(Offset(100f, 100f))
    val foodPosition: StateFlow<Offset> get() = _foodPosition

    private val _snakeDirection = MutableStateFlow(Offset(1f, 0f)) // Başlangıçta sağa gidiyor
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
                // Hata yönetimi eklenebilir
            }
        }
    }

    fun saveSnake(name: String, score: Int) {
        viewModelScope.launch {
            val snake = Snake(name = name, score = score)
            repository.insertSnake(snake)
        }
    }

    // Yemek konumunu rastgele belirleme
    fun initializeFood(width: Float, height: Float, blockSize: Float) {
        _foodPosition.value = randomOffset(width, height, blockSize)
    }

    // Yılan hareketi fonksiyonu
    fun moveSnake(blockSize: Float, canvasSize: Size, name: String, onGameOver: (Int) -> Unit) {
        val newHead = _snakeBody.value.first().copy(
            x = _snakeBody.value.first().x + blockSize * _snakeDirection.value.x,
            y = _snakeBody.value.first().y + blockSize * _snakeDirection.value.y
        )

        // Duvara veya kendine çarpma kontrolü
        if (newHead.x < 0 || newHead.x + blockSize > canvasSize.width ||
            newHead.y < 0 || newHead.y + blockSize > canvasSize.height ||
            _snakeBody.value.drop(1).contains(newHead)
        ) {
            _isGameOver.value = true
            saveSnake(name, _score.intValue)
            onGameOver(_score.intValue)
            return
        }

        // Yem yeme kontrolü
        val headCenter = newHead + Offset(blockSize / 2, blockSize / 2)
        val foodCenter = _foodPosition.value + Offset(blockSize / 2, blockSize / 2)
        if ((headCenter - foodCenter).getDistance() < blockSize) {
            _snakeBody.value = listOf(newHead) + _snakeBody.value
            _foodPosition.value = randomOffset(canvasSize.width, canvasSize.height, blockSize)
            _score.value += 10
        } else {
            _snakeBody.value = listOf(newHead) + _snakeBody.value.dropLast(1)
        }
    }

    // Yön güncelleme fonksiyonu
    fun updateDirection(dragX: Float, dragY: Float) {
        _snakeDirection.value = when {
            abs(dragX) > abs(dragY) -> if (dragX > 0) Offset(1f, 0f) else Offset(-1f, 0f)
            else -> if (dragY > 0) Offset(0f, 1f) else Offset(0f, -1f)
        }
    }
    private fun randomOffset(width: Float, height: Float, blockSize: Float): Offset {
        val randomX = (0 until (width / blockSize).toInt()).random() * blockSize
        val randomY = (0 until (height / blockSize).toInt()).random() * blockSize
        return Offset(randomX, randomY)
    }

}

package com.example.feedthesnake.viewModel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feedthesnake.constants.SpeedConstants
import com.example.feedthesnake.model.Snake
import com.example.feedthesnake.repository.SnakeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SnakeViewModel @Inject constructor(private val repository: SnakeRepository) : ViewModel() {
    private val _topSnakes = MutableStateFlow<List<Snake>>(emptyList())
    val topSnakes: StateFlow<List<Snake>> get() = _topSnakes

    private val _score = mutableIntStateOf(0)
    val score: State<Int> = _score

    private val _difficulty = mutableStateOf("Normal") // Varsayılan zorluk seviyesi
    val difficulty: State<String> get() = _difficulty

    init {
        fetchTopSnakes()
    }

    private fun fetchTopSnakes() {
        viewModelScope.launch {
            try {
            val scores = repository.getTopSnakes()
            _topSnakes.value = scores
            } catch (e: Exception) {
                // Hata logu ekleyin
                Log.e("SnakeViewModel", "Error fetching top snakes", e)
            }
        }
    }

    fun saveSnake(name: String, score: Int) {
        viewModelScope.launch {
            val snake = Snake(name = name, score = score)
            repository.insertSnake(snake)
        }
    }


    fun setDifficulty(selectedDifficulty: String) {
        _difficulty.value = selectedDifficulty
    }

    fun getSpeed(): Long {
        return when (_difficulty.value) {
            "Kolay" -> SpeedConstants.EASY_SPEED
            "Normal" -> SpeedConstants.NORMAL_SPEED
            "Zor" -> SpeedConstants.HARD_SPEED
            else -> SpeedConstants.NORMAL_SPEED
        }
    }
}
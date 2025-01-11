package com.example.feedthesnake.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private fun fetchTopSnakes() {
        viewModelScope.launch {
            val scores = repository.getTopSnakes()
            _topSnakes.value = scores
        }
    }

    fun saveSnake(name: String, score: Int) {
        viewModelScope.launch {
            val snake = Snake(name = name, score = score)
            repository.insertSnake(snake)
        }
    }
}
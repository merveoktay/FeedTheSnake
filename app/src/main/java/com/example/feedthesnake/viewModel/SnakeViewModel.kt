package com.example.feedthesnake.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feedthesnake.model.Snake
import com.example.feedthesnake.repository.SnakeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SnakeViewModel(private val repository: SnakeRepository) : ViewModel() {
    private val _topSnakes = MutableStateFlow<List<Snake>>(emptyList())
    val topSnakes: StateFlow<List<Snake>> get() = _topSnakes

    private fun fetchTopSnakes() {
        viewModelScope.launch {
            val scores = repository.getTopSnakes()
            _topSnakes.value = scores
        }
    }

    fun addSnake(snake: Snake) {
        viewModelScope.launch {
            repository.insertSnake(snake)
            fetchTopSnakes() // Refresh the list
        }
    }
}
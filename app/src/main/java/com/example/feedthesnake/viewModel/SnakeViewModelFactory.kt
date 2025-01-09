package com.example.feedthesnake.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.feedthesnake.repository.SnakeRepository

class SnakeViewModelFactory (private val repository: SnakeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SnakeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SnakeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
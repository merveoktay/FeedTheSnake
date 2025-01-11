package com.example.feedthesnake.repository

import com.example.feedthesnake.dao.SnakeDao
import com.example.feedthesnake.model.Snake
import javax.inject.Inject

class SnakeRepository @Inject constructor(private val snakeDao: SnakeDao) {
    suspend fun getTopSnakes(): List<Snake> {
        return snakeDao.getTopSnakes()
    }

    suspend fun insertSnake(snake: Snake) {
        snakeDao.insertSnake(snake)
    }
}
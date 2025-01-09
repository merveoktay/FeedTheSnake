package com.example.feedthesnake.repository

import com.example.feedthesnake.dao.SnakeDao
import com.example.feedthesnake.model.Snake

class SnakeRepository(private val snakeDao: SnakeDao) {
    suspend fun getTopSnakes(): List<Snake> {
        return snakeDao.getTopSnakes()
    }

    suspend fun insertSnake(snake: Snake) {
        snakeDao.insertSnake(snake)
    }
}
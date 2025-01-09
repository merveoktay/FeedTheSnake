package com.example.feedthesnake.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.feedthesnake.model.Snake

@Dao
interface SnakeDao {
    @Insert
    suspend fun insertSnake(snake: Snake)

    @Query("SELECT * FROM snakes ORDER BY score DESC LIMIT 10")
    suspend fun getTopSnakes(): List<Snake>
}

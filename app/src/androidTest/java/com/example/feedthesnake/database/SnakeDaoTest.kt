package com.example.feedthesnake.database


import androidx.arch.core.executor.testing.InstantTaskExecutorRule

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import androidx.test.runner.AndroidJUnit4
import com.example.feedthesnake.configs.AppDatabase
import com.example.feedthesnake.dao.SnakeDao
import com.example.feedthesnake.model.Snake
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.runner.RunWith



import java.io.IOException

@SmallTest
@RunWith(AndroidJUnit4::class)
class SnakeDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var snakeDao: SnakeDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        snakeDao = database.snakeDao()
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertSnakeAndRetrieve() = runTest {
        // Arrange
        val snake = Snake(name = "Player1", score = 100)

        // Act
        snakeDao.insertSnake(snake)
        val allSnakes = snakeDao.getTopSnakes()

        // Assert
        Assert.assertEquals(1, allSnakes.size)
        Assert.assertEquals("Player1", allSnakes[0].name)
        Assert.assertEquals(100, allSnakes[0].score)
    }

    @Test
    fun insertMultipleSnakesAndGetTop10() = runTest {
        // Arrange
        val snakeList = listOf(
            Snake(12, name = "Player1", score = 300),
            Snake(134, name = "Player2", score = 250),
            Snake(24, name = "Player3", score = 200),
            Snake(33, name = "Player4", score = 150),
            Snake(45, name = "Player5", score = 100),
            Snake(65, name = "Player6", score = 50),
            Snake(36, name = "Player7", score = 20),
            Snake(57, name = "Player8", score = 10),
            Snake(48, name = "Player9", score = 5),
            Snake(39, name = "Player10", score = 2),
            Snake(17, name = "Player11", score = 1)
        )

        // Act
        snakeList.forEach { snake -> snakeDao.insertSnake(snake) }
        val topSnakes = snakeDao.getTopSnakes()

        // Assert
        Assert.assertEquals(10, topSnakes.size)
        Assert.assertEquals("Player1", topSnakes[0].name) // Highest score
        Assert.assertEquals(300, topSnakes[0].score) // Highest score
        Assert.assertEquals("Player10", topSnakes[9].name) // Lowest of top 10
        Assert.assertTrue(topSnakes[0].score > topSnakes[1].score) // Check order of scores
    }


    @Test
    fun insertSnakeAndGetEmptyList() = runTest {
        // Act
        val emptyList = snakeDao.getTopSnakes()

        // Assert
        Assert.assertTrue(emptyList.isEmpty())
    }

    @Test
    fun insertSnakesAndCheckSortingByScore() = runTest {
        // Arrange
        val snakeList = listOf(
            Snake(70, name = "Player1", score = 200),
            Snake(19, name = "Player2", score = 500),
            Snake(22, name = "Player3", score = 300)
        )

        // Act
        snakeList.forEach { snake -> snakeDao.insertSnake(snake) }
        val topSnakes = snakeDao.getTopSnakes()

        // Assert
        Assert.assertEquals("Player2", topSnakes[0].name) // Highest score first
        Assert.assertEquals(500, topSnakes[0].score) // Highest score first
        Assert.assertEquals("Player3", topSnakes[1].name)
        Assert.assertEquals(300, topSnakes[1].score)
        Assert.assertEquals("Player1", topSnakes[2].name)
        Assert.assertEquals(200, topSnakes[2].score)
    }
}

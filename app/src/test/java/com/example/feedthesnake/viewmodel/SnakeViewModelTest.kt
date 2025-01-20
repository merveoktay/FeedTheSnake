package com.example.feedthesnake.viewmodel

import app.cash.turbine.test
import com.example.feedthesnake.model.Snake
import com.example.feedthesnake.repository.SnakeRepository
import com.example.feedthesnake.viewModel.SnakeViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`


class SnakeViewModelTest {

    private lateinit var viewModel: SnakeViewModel
    private lateinit var repository: SnakeRepository

    @Before
    fun setup() {
        repository = mock(SnakeRepository::class.java)
        viewModel = SnakeViewModel(repository)
    }

    @Test
    fun `fetchTopSnakes loads data successfully`() = runTest {
        // Arrange
        val snakeList = listOf(Snake(0,"Player1", 100), Snake(1,"Player2", 200))
        `when`(repository.getTopSnakes()).thenReturn(snakeList)

        // Act
        viewModel.fetchTopSnakes()

        // Assert
        viewModel.topSnakes.test {
            val result = awaitItem()
            assertEquals(2, result.size)
            assertEquals("Player1", result[0].name)
            assertEquals(100, result[0].score)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `saveSnake saves data to repository`() = runTest {
        // Arrange
        val snake = Snake(3,"Player1", 150)

        // Act
        viewModel.saveSnake(snake.name, snake.score)

        // Assert
        verify(repository, times(1)).insertSnake(snake)
    }

    @Test
    fun `setDifficulty updates difficulty level`() {
        // Act
        viewModel.setDifficulty("Zor")

        // Assert
        assertEquals("Zor", viewModel.difficulty.value)
    }

    @Test
    fun `getSpeed returns correct speed for difficulty levels`() {
        // Act & Assert
        viewModel.setDifficulty("Kolay")
        assertEquals(500L, viewModel.getSpeed())

        viewModel.setDifficulty("Normal")
        assertEquals(300L, viewModel.getSpeed())

        viewModel.setDifficulty("Zor")
        assertEquals(100L, viewModel.getSpeed())
    }
}
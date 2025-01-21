package com.example.feedthesnake.viewmodel


import com.example.feedthesnake.model.Snake
import com.example.feedthesnake.repository.SnakeRepository
import com.example.feedthesnake.viewModel.SnakeViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SnakeViewModelTest {

    private lateinit var viewModel: SnakeViewModel
    private lateinit var repository: SnakeRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {

        Dispatchers.setMain(testDispatcher)
        repository = mockk(relaxed = true)
        viewModel = SnakeViewModel(repository)
    }

    @After
    fun tearDown() {

        Dispatchers.resetMain()
    }

    @Test
    fun `fetchTopSnakes loads data successfully`() = runTest {
        // Arrange
        val snakeList = listOf(
            Snake(156, "Player1", 100),
            Snake(23, "Player2", 200)
        )
        coEvery { repository.getTopSnakes() } returns snakeList

        // Act
        viewModel.fetchTopSnakes()
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val result = viewModel.topSnakes.value
        assertEquals(2, result.size)
        assertEquals("Player1", result[0].name)
        assertEquals(100, result[0].score)
        assertEquals("Player2", result[1].name)
        assertEquals(200, result[1].score)
    }

    @Test
    fun `saveSnake saves data to repository`() = runTest {
        // Arrange
        val snake = Snake(45, "Player1", 150)

        // Act
        viewModel.saveSnake(snake.name, snake.score)
        testScheduler.advanceUntilIdle()

        // Assert
        coVerify(exactly = 1) { repository.insertSnake(any()) }
    }
}

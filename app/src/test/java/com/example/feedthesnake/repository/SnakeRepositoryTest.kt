package com.example.feedthesnake.repository

import com.example.feedthesnake.dao.SnakeDao
import com.example.feedthesnake.model.Snake
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class SnakeRepositoryTest {

    @Mock
    private lateinit var snakeDao: SnakeDao

    private lateinit var repository: SnakeRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = SnakeRepository(snakeDao)
    }

    @Test
    fun `getTopSnakes returns list of snakes`() = runTest {
        // Mock behavior
        val mockSnakes = listOf(Snake(1,"Alice", 100), Snake(2,"Bob", 80))
        `when`(snakeDao.getTopSnakes()).thenReturn(mockSnakes)

        // Call method
        val result = repository.getTopSnakes()

        // Verify results
        assertEquals(mockSnakes, result)
        verify(snakeDao).getTopSnakes()
    }

    @Test
    fun `insertSnake calls snakeDao insert method`() = runTest {
        val newSnake = Snake(0,"Charlie", 90)

        // Call method
        repository.insertSnake(newSnake)

        // Verify interaction
        verify(snakeDao).insertSnake(newSnake)
    }
}
package com.example.houses_data

import com.example.core_common.AppError
import com.example.houses_data.remote.datasource.HouseRemoteDataSource
import com.example.houses_data.remote.repository.HouseRepositoryImpl
import com.example.houses_domain.model.Head
import com.example.houses_domain.model.House
import com.example.houses_domain.model.Trait
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import com.example.core_common.Result
import junit.framework.TestCase.assertEquals
import java.io.IOException

@ExperimentalCoroutinesApi
class HouseRepositoryImplTest {

    private lateinit var remote: HouseRemoteDataSource
    private lateinit var repository: HouseRepositoryImpl

    @Before
    fun setup() {
        remote = mockk() // Initialize the mock for HouseRemoteDataSource
        repository = HouseRepositoryImpl(remote) // Inject the mocked remote data source
    }

    @Test
    fun getHouses_returnsSuccessResultWithHouses_whenRemoteSucceeds() = runTest {
        val sampleHouse = House(
            id = "1",
            houseColours = "Scarlet and Gold",
            animal = "Lion",
            founder = "Godric Gryffindor",
            element = "Fire",
            ghost = "Nearly Headless Nick",
            commonRoom = "Gryffindor Tower",
            heads = listOf(
                Head(id = "h1", firstName = "Minerva", lastName = "McGonagall")
            ),
            traits = listOf(
                Trait(id = "t1", name = "Courage"),
                Trait(id = "t2", name = "Bravery")
            ),
            name = "Gryffindor"
        )
        // Arrange
        val expectedHouses = listOf(sampleHouse)
        coEvery { remote.getHouse() } returns expectedHouses

        // Act
        val result = repository.getHouses()

        // Assert
        assertTrue(result is Result.Success)
        val successResult = result as Result.Success
        assertEquals(expectedHouses, successResult.data)
        assertEquals(1, successResult.data.size)
    }

    @Test
    fun getHouses_returnsSuccessResultWithEmptyList_whenRemoteReturnsEmpty() = runTest {
        // Arrange
        coEvery { remote.getHouse() } returns emptyList()

        // Act
        val result = repository.getHouses()

        // Assert
        assertTrue(result is Result.Success)
        val successResult = result as Result.Success
        assertTrue(successResult.data.isEmpty())
    }

    @Test
    fun getHouses_returnsErrorResultWithNetworkError_whenRemoteThrowsIOException() = runTest {
        // Arrange
        coEvery { remote.getHouse() } throws IOException("Failed to connect to host")

        // Act
        val result = repository.getHouses()

        // Assert
        assertTrue(result is Result.Error)
        val errorResult = result as Result.Error
        assertTrue(errorResult.error is AppError.Network)
    }

    @Test
    fun getHouses_returnsErrorResultWithUnexpectedError_forOtherExceptionsFromRemote() = runTest {
        // Arrange
        val genericException = IllegalStateException("Something went really wrong!")
        coEvery { remote.getHouse() } throws genericException

        // Act
        val result = repository.getHouses()

        // Assert
        assertTrue(result is Result.Error)
        val errorResult = result as Result.Error
        assertTrue(errorResult.error is AppError.Unexpected)
        val unexpectedError = errorResult.error as AppError.Unexpected
        assertEquals(genericException, unexpectedError.throwable) // Verify the original throwable is wrapped
    }
}
package com.example.houses_domain.usecase

import com.example.core_common.AppError
import com.example.houses_domain.model.Head
import com.example.houses_domain.model.House
import com.example.houses_domain.model.Trait
import com.example.houses_domain.repository.HouseRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import com.example.core_common.Result
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue

@ExperimentalCoroutinesApi
class GetHousesUseCaseTest {

    private lateinit var repository: HouseRepository
    private lateinit var getHousesUseCase: GetHousesUseCase

    @Before
    fun setup() {
        // Initialize a mock instance of HouseRepository before each test
        repository = mockk()
        getHousesUseCase = GetHousesUseCase(repository)
    }

    @Test
    fun invoke_returnsHouses_whenRepositorySucceeds() = runTest {
        // Given: The repository returns a list of sample houses
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
        val expectedHouses = listOf(sampleHouse)
        coEvery { repository.getHouses() } returns Result.Success(expectedHouses)

        // When: The use case is invoked
        val result: Result<List<House>>  = getHousesUseCase()

        // Then: The result should be the expected list of houses
        assertTrue(result is Result.Success)
        val successResult = result as Result.Success
        assertEquals(expectedHouses, successResult.data)
        assertEquals(1, successResult.data.size)
        assertEquals(sampleHouse.id, successResult.data[0].id)

    }

    @Test
    fun invoke_returnsEmptyList_whenRepositoryReturnsEmpty() = runTest {
        // Given: The repository returns an empty list
        coEvery { repository.getHouses() } returns Result.Success(emptyList())

        // When: The use case is invoked
        val result = getHousesUseCase()

        // Then: The result should be an empty list
        assertTrue(result is Result.Success)
        val successResult = result as Result.Success
        assertTrue(successResult.data.isEmpty())
    }

    @Test
    fun invoke_throwsException_whenRepositoryThrowsException() = runTest {
        // Given: The repository throws an exception
        val expectedError = Result.Error(AppError.Network)
        coEvery { repository.getHouses() } returns expectedError // Mocking your custom Result.Error()

        // When: The use case is invoked
        val result = getHousesUseCase()

        // Then: The result should be an Error and contain the expected AppError
        assertTrue(result is Result.Error)
        val errorResult = result as Result.Error
        assertTrue(errorResult.error is AppError.Network)
    }

    @Test
    fun invoke_returnsErrorResultWithHttpError_whenRepositoryReturnsHttpError() = runTest {
        // Given: The repository returns an Error Result with AppError.HttpError
        val expectedHttpError = AppError.HttpError(404, "Not Found")
        coEvery { repository.getHouses() } returns Result.Error(expectedHttpError)

        // When: The use case is invoked
        val result = getHousesUseCase()

        // Then: The result should be an Error and contain the expected AppError.HttpError
        assertTrue(result is Result.Error)
        val errorResult = result as Result.Error
        assertTrue(errorResult.error is AppError.HttpError)
        val httpError = errorResult.error as AppError.HttpError
        assertEquals(expectedHttpError.code, httpError.code)
        assertEquals(expectedHttpError.message, httpError.message)
    }

    @Test
    fun invoke_returnsErrorResultWithUnexpectedError_whenRepositoryReturnsUnexpectedError() = runTest {
        // Given: The repository returns an Error Result with AppError.Unexpected
        val unexpectedThrowable = IllegalStateException("Something went wrong!")
        coEvery { repository.getHouses() } returns Result.Error(AppError.Unexpected(unexpectedThrowable))

        // When: The use case is invoked
        val result = getHousesUseCase()

        // Then: The result should be an Error and contain the expected AppError.Unexpected
        assertTrue(result is Result.Error)
        val errorResult = result as Result.Error
        assertTrue(errorResult.error is AppError.Unexpected)
        val unexpectedError = errorResult.error as AppError.Unexpected
        assertEquals(unexpectedThrowable, unexpectedError.throwable)
    }
}
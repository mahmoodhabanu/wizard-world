package com.example.houses_presentation.houselist

import com.example.core_common.AppError
import com.example.houses_domain.usecase.GetHousesUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import com.example.core_common.Result
import com.example.core_common.UIState
import com.example.houses_domain.model.Head
import com.example.houses_domain.model.House
import com.example.houses_domain.model.Trait
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle

@OptIn(ExperimentalCoroutinesApi::class) // Opt-in for Experimental Coroutines API
class HouseListViewModelTest {

    // Use UnconfinedTestDispatcher for immediate execution of coroutines
    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var getHousesUseCase: GetHousesUseCase
    private lateinit var viewModel: HouseListViewModel // Declared here, initialized in tests
    private val sampleHouse = House(
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

    @Before
    fun setup() {
        // Set the Main dispatcher to our test dispatcher so viewModelScope uses it
        Dispatchers.setMain(testDispatcher)
        // Initialize the mock use case before each test
        getHousesUseCase = mockk()
    }

    @After
    fun tearDown() {
        // Reset the Main dispatcher to the original one after each test
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchHouses_success_emitsIdleLoadingThenSuccess`() = runTest(testDispatcher) {
        // Given: Dummy data and mock the use case to return success
        val dummyHouses = listOf(
            sampleHouse
        )
        coEvery { getHousesUseCase() } returns Result.Success(dummyHouses)

        // Create a Channel to receive UIState emissions
        val statesChannel = Channel<UIState<List<House>>>()

        viewModel = HouseListViewModel(getHousesUseCase)
        val job = backgroundScope.launch { // Launch collector in backgroundScope
            viewModel.uiState.collect { statesChannel.send(it) }
        }
        advanceUntilIdle() // Ensure all coroutines in viewModelScope have completed

        // Then: Assert
        assertEquals(UIState.Success(dummyHouses), statesChannel.receive())

        statesChannel.cancel() // Close the channel
        job.cancel() // Cancel the collector coroutine
    }

    @Test
    fun `fetchHouses_error_emitsIdleLoadingThenError`() = runTest(testDispatcher) {
        coEvery { getHousesUseCase() } returns Result.Error(AppError.Network)

        val statesChannel = Channel<UIState<List<House>>>()

        val viewModel = HouseListViewModel(getHousesUseCase)
        val job = backgroundScope.launch {
            viewModel.uiState.collect { statesChannel.send(it) }
        }

        assertEquals(UIState.Error(AppError.Network), statesChannel.receive())

        statesChannel.cancel()
        job.cancel()
    }
}
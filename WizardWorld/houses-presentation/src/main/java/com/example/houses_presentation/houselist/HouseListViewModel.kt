package com.example.houses_presentation.houselist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_common.UIState
import com.example.houses_domain.model.House
import com.example.houses_domain.usecase.GetHousesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.core_common.Result

@HiltViewModel
class HouseListViewModel @Inject constructor(
    private val getHousesUseCase: GetHousesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<List<House>>>(UIState.Idle)
    val uiState: StateFlow<UIState<List<House>>> = _uiState.asStateFlow()

    init {
        fetchHouses()
    }

    fun fetchHouses() {
        _uiState.value = UIState.Loading
        viewModelScope.launch {
            when (val result = getHousesUseCase()) {
                is Result.Success -> {
                    _uiState.value = UIState.Success(result.data)
                }
                is Result.Error -> {
                    _uiState.value = UIState.Error(result.error)
                }
            }
        }
    }
}
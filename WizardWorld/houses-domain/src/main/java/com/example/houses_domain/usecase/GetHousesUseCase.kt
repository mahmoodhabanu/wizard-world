package com.example.houses_domain.usecase

import com.example.houses_domain.model.House
import com.example.houses_domain.repository.HouseRepository
import javax.inject.Inject
import com.example.core_common.Result

class GetHousesUseCase @Inject constructor(
    private val repository: HouseRepository
) {
    suspend operator fun invoke(): Result<List<House>> {
        return repository.getHouses()
    }
}
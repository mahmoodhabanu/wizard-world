package com.example.houses_domain.repository

import com.example.houses_domain.model.House
import com.example.core_common.Result

interface HouseRepository {
    suspend fun getHouses(): Result<List<House>>
}
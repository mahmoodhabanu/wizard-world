package com.example.houses_data.remote.datasource

import com.example.houses_data.remote.api.HouseAPI
import com.example.houses_domain.model.House
import javax.inject.Inject

class HouseRemoteDataSource @Inject constructor(
    private val api: HouseAPI
) {
    suspend fun getHouse(): List<House> = api.getHouses()
}
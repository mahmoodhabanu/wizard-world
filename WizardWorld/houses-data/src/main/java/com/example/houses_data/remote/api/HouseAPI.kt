package com.example.houses_data.remote.api

import com.example.houses_domain.model.House
import retrofit2.http.GET

interface HouseAPI {
    @GET("Houses")
    suspend fun getHouses(): List<House>
}
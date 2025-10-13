package com.example.houses_data.remote.repository

import com.example.core_common.AppError
import com.example.houses_data.remote.datasource.HouseRemoteDataSource
import com.example.houses_domain.model.House
import com.example.houses_domain.repository.HouseRepository
import javax.inject.Inject
import com.example.core_common.Result

class HouseRepositoryImpl @Inject constructor(
    private val remote: HouseRemoteDataSource
): HouseRepository {
    override suspend fun getHouses(): Result<List<House>> = try {
        val products = remote.getHouse()
        Result.Success(products)
    } catch (e: Exception) {
        Result.Error(AppError.fromException(e))
    }
}
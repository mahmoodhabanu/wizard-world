package com.example.houses_data.remote.repository

import com.example.core_common.AppError
import com.example.houses_data.remote.datasource.HouseRemoteDataSource
import com.example.houses_domain.model.House
import com.example.houses_domain.repository.HouseRepository
import javax.inject.Inject
import com.example.core_common.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HouseRepositoryImpl @Inject constructor(
    private val remote: HouseRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): HouseRepository {
    override suspend fun getHouses(): Result<List<House>> = withContext(ioDispatcher) {
        try {
            val products = remote.getHouse()
            Result.Success(products)
        } catch (e: Exception) {
            Result.Error(AppError.fromException(e))
        }
    }
}
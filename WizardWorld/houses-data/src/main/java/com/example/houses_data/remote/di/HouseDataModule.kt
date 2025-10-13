package com.example.houses_data.remote.di

import com.example.houses_data.remote.api.HouseAPI
import com.example.houses_data.remote.repository.HouseRepositoryImpl
import com.example.houses_domain.repository.HouseRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HouseDataModule {

    @Binds
    @Singleton
    abstract fun bindHouseRepository(
        houseRepositoryImpl: HouseRepositoryImpl
    ): HouseRepository

    companion object {
        @Provides
        @Singleton
        fun provideWizardWorldApi(retrofit: Retrofit): HouseAPI {
            return retrofit.create(HouseAPI::class.java)
        }
    }
}
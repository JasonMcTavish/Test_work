package ru.test.testwork.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.test.testwork.api.TestAPI


@InstallIn(SingletonComponent::class)
@Module
class ApiModule {
    @Provides
    fun provideTestAPI(): TestAPI {
        return TestAPI.getInstance()
    }
}
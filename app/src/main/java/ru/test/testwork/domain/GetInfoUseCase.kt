package ru.test.testwork.domain

import ru.test.testwork.api.ResponseInfoDto
import ru.test.testwork.data.Repository
import javax.inject.Inject

class GetInfoUseCase @Inject constructor(private val repository: Repository) {
    suspend fun executeInfo(id: String): ResponseInfoDto {
        return repository.getInfo(id)
    }
}
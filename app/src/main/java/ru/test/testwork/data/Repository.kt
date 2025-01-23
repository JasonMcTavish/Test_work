package ru.test.testwork.data

import ru.test.testwork.api.ResponseInfoDto
import ru.test.testwork.api.TestAPI
import javax.inject.Inject

class Repository @Inject constructor(private val api: TestAPI) {
    suspend fun getInfo(id: String): ResponseInfoDto {
        return api.getInfo(id)
    }
}
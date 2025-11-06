package com.guri.guriquizzy.data.mapper

import com.guri.guriquizzy.data.model.QuizQuestion
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

object QuizJsonMapper {
    private val json by lazy {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
    }

    fun fromJson(jsonString: String): List<QuizQuestion> {
        return json.decodeFromString(ListSerializer(QuizQuestion.serializer()), jsonString)
    }
}
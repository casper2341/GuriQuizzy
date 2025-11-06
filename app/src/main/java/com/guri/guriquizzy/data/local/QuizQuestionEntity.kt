package com.guri.guriquizzy.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.guri.guriquizzy.data.model.QuizQuestion

@Entity(tableName = "quiz_questions")
data class QuizQuestionEntity(
    @PrimaryKey val id: Int,
    val question: String,
    val optionsJson: String,
    val correctOptionIndex: Int
)

fun QuizQuestionEntity.toDomain(): QuizQuestion {
    val options = optionsJson.split("\u0001").filter { it.isNotEmpty() }
    return QuizQuestion(
        id = id,
        question = question,
        options = options,
        correctOptionIndex = correctOptionIndex
    )
}

fun QuizQuestion.toEntity(): QuizQuestionEntity {
    val joined = options.joinToString(separator = "\u0001")
    return QuizQuestionEntity(
        id = id,
        question = question,
        optionsJson = joined,
        correctOptionIndex = correctOptionIndex
    )
}
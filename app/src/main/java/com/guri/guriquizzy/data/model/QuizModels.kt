package com.guri.guriquizzy.data.model

import kotlinx.serialization.Serializable

@Serializable
data class QuizQuestion(
    val id: Int,
    val question: String,
    val options: List<String>,
    val correctOptionIndex: Int
)
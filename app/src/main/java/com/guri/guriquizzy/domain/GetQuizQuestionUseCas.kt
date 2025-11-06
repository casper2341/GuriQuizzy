package com.guri.guriquizzy.domain

import com.guri.guriquizzy.data.model.QuizQuestion
import com.guri.guriquizzy.data.repository.QuizRepository

class GetQuizQuestionsUseCase(
    private val quizRepository: QuizRepository
) {
    suspend operator fun invoke(): Result<List<QuizQuestion>> {
        return try {
            val questions = quizRepository.getAllQuestions()
            Result.success(questions)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
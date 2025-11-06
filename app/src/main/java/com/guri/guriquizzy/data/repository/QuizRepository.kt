package com.guri.guriquizzy.data.repository

import com.guri.guriquizzy.data.model.QuizQuestion

interface QuizRepository {
    suspend fun getAllQuestions(): List<QuizQuestion>
}

class QuizRepositoryImpl(
    private val localDataSource: QuizLocalDataSource
) : QuizRepository {

    override suspend fun getAllQuestions(): List<QuizQuestion> {
        return localDataSource.getAllQuestions()
    }
}
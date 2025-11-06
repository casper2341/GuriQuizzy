package com.guri.guriquizzy.data.repository

import com.guri.guriquizzy.data.local.AppDatabase
import com.guri.guriquizzy.data.local.toDomain
import com.guri.guriquizzy.data.model.QuizQuestion

interface QuizLocalDataSource {
    suspend fun getAllQuestions(): List<QuizQuestion>
}

class QuizLocalDataSourceImpl(
    private val appDatabase: AppDatabase
) : QuizLocalDataSource {

    override suspend fun getAllQuestions(): List<QuizQuestion> {
        return appDatabase.quizQuestionDao().getAll().map { it.toDomain() }
    }
}
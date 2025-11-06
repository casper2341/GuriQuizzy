package com.guri.guriquizzy.di
import android.content.Context
import com.guri.guriquizzy.data.local.AppDatabase
import com.guri.guriquizzy.data.repository.QuizLocalDataSourceImpl
import com.guri.guriquizzy.data.repository.QuizRepository
import com.guri.guriquizzy.data.repository.QuizRepositoryImpl
import com.guri.guriquizzy.domain.GetQuizQuestionsUseCase

object AppModule {

    private lateinit var appDatabase: AppDatabase

    fun initialize(context: Context) {
        appDatabase = AppDatabase.getInstance(context)
    }

    fun provideQuizRepository(): QuizRepository {
        val localDataSource = QuizLocalDataSourceImpl(appDatabase)
        return QuizRepositoryImpl(localDataSource)
    }

    fun provideGetQuizQuestionsUseCase(): GetQuizQuestionsUseCase {
        return GetQuizQuestionsUseCase(provideQuizRepository())
    }
}
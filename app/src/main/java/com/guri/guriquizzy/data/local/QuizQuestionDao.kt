package com.guri.guriquizzy.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QuizQuestionDao {
    @Query("SELECT * FROM quiz_questions")
    suspend fun getAll(): List<QuizQuestionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<QuizQuestionEntity>)
}
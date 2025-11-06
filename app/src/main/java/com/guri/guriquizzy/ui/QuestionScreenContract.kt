package com.guri.guriquizzy.ui

import com.guri.guriquizzy.data.model.QuizQuestion

data class QuestionUIState(
    val allQuestions: List<QuizQuestion> = emptyList(),
    val currentIndex: Int = 0,
    val selectedOptionIndex: Int? = null,
    val score: Int = 0,
    val streak: Int = 0,
    val maxStreak: Int = 0,
    val skippedCount: Int = 0,
    val shouldNavigateToResult: Boolean = false
) {
    val currentQuestion: QuizQuestion? get() = allQuestions.getOrNull(currentIndex)
    val isAnswerSelected: Boolean get() = selectedOptionIndex != null
    val isAnswerCorrect: Boolean get() =
        selectedOptionIndex != null &&
                currentQuestion != null &&
                selectedOptionIndex == currentQuestion!!.correctOptionIndex
    val isLastQuestion: Boolean get() = currentIndex >= allQuestions.size - 1
}
package com.guri.guriquizzy.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guri.guriquizzy.di.AppModule
import com.guri.guriquizzy.ui.QuestionUIState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuestionViewModel() : ViewModel() {
    private val _state = MutableStateFlow(QuestionUIState())
    val state: StateFlow<QuestionUIState> = _state


    private val getQuizQuestionsUseCase = AppModule.provideGetQuizQuestionsUseCase()
    private var autoRedirectJob: Job? = null
    init {
        loadQuestions()
    }

    private fun loadQuestions() {
        viewModelScope.launch {
            getQuizQuestionsUseCase().onSuccess { questions ->
                _state.value = _state.value.copy(allQuestions = questions, currentIndex = 0)
            }
        }
    }

    fun selectOption(optionIndex: Int) {
        if (!_state.value.isAnswerSelected) {
            val currentState = _state.value
            val currentQuestion = currentState.currentQuestion
            val isCorrect = currentQuestion != null && optionIndex == currentQuestion.correctOptionIndex
            val newScore = if (isCorrect) currentState.score + 1 else currentState.score

            val newStreak = if (isCorrect) {
                currentState.streak + 1
            } else {
                0
            }
            val newMaxStreak = maxOf(currentState.maxStreak, newStreak)

            _state.value = currentState.copy(
                selectedOptionIndex = optionIndex,
                score = newScore,
                streak = newStreak,
                maxStreak = newMaxStreak
            )

            startAutoRedirect()
        }
    }

    private fun startAutoRedirect() {
        autoRedirectJob?.cancel()

        autoRedirectJob = viewModelScope.launch {
            delay(2000)
            moveToNextQuestion()
        }
    }

    fun moveToNextQuestion() {
        autoRedirectJob?.cancel()

        val currentState = _state.value
        if (currentState.isLastQuestion) {
            _state.value = currentState.copy(shouldNavigateToResult = true)
        } else {
            val nextIndex = currentState.currentIndex + 1
            _state.value = currentState.copy(
                currentIndex = nextIndex,
                selectedOptionIndex = null
            )
        }
    }

    fun skipQuestion() {
        autoRedirectJob?.cancel()

        val currentState = _state.value
        if (currentState.isLastQuestion) {
            _state.value = currentState.copy(
                shouldNavigateToResult = true,
                skippedCount = currentState.skippedCount + 1,
                streak = 0
            )
        } else {
            val nextIndex = currentState.currentIndex + 1
            _state.value = currentState.copy(
                currentIndex = nextIndex,
                selectedOptionIndex = null,
                skippedCount = currentState.skippedCount + 1,
                streak = 0
            )
        }
    }

    fun onNavigationHandled() {
        _state.value = _state.value.copy(shouldNavigateToResult = false)
    }

    fun resetQuiz() {
        _state.value = _state.value.copy(
            currentIndex = 0,
            selectedOptionIndex = null,
            score = 0,
            streak = 0,
            maxStreak = 0,
            skippedCount = 0,
            shouldNavigateToResult = false
        )
        autoRedirectJob?.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        autoRedirectJob?.cancel()
    }
}
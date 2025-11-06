package com.guri.guriquizzy.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.guri.guriquizzy.navigation.QuestionScreen
import com.guri.guriquizzy.navigation.FinalScoreScreen
import com.guri.guriquizzy.ui.home.QuestionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    vm: QuestionViewModel
) {
    val uiState by vm.state.collectAsState()
    val question = uiState.currentQuestion
    val totalQuestions = uiState.allQuestions.size.takeIf { it > 0 } ?: 10
    val currentQuestionNumber = uiState.currentIndex + 1
    val progress = currentQuestionNumber.toFloat() / totalQuestions

    LaunchedEffect(uiState.shouldNavigateToResult) {
        if (uiState.shouldNavigateToResult) {
            navController.navigate(FinalScoreScreen(finalScore = uiState.score)) {
                popUpTo(QuestionScreen) { inclusive = false }
            }
            vm.onNavigationHandled()
        }
    }

    BackHandler {
    }

    Scaffold(
        topBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                TopAppBar(
                    title = { Text(text = "Question $currentQuestionNumber of $totalQuestions") }
                )
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.fillMaxWidth()
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .background(
                            color = when {
                                uiState.streak >= 3 -> Color(0xFFFFD700).copy(alpha = 0.2f)
                                else -> Color(0xFFEEEEEE).copy(alpha = 0.3f)
                            },
                            shape = RoundedCornerShape(12.dp)
                        )
                        .border(
                            width = 2.dp,
                            color = when {
                                uiState.streak >= 3 -> Color(0xFFFFD700)
                                else -> Color(0xFFCCCCCC)
                            },
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "🔥",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontSize = MaterialTheme.typography.headlineMedium.fontSize *
                                        (1f + (uiState.streak.coerceAtMost(5)
                                            .toFloat() / 10f))
                            )
                        )

                        Column(
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Text(
                                text = when (uiState.streak) {
                                    0 -> "Start your streak!"
                                    1 -> "1 correct answer"
                                    else -> "${uiState.streak} correct in a row!"
                                },
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )

                            if (uiState.streak < 10) {
                                val nextMilestone = when {
                                    uiState.streak < 3 -> 3
                                    uiState.streak < 5 -> 5
                                    uiState.streak < 7 -> 7
                                    else -> 10
                                }

                                Text(
                                    text = "${uiState.streak}/${nextMilestone} to next milestone",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = question?.question ?: "Loading...",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            question?.options?.forEachIndexed { index, optionText ->
                val isSelected = uiState.selectedOptionIndex == index
                val isCorrect = index == question.correctOptionIndex
                val isWrong = isSelected && !isCorrect && uiState.isAnswerSelected
                val showCorrectMarker = uiState.isAnswerSelected && isCorrect
                val showCorrectAnswerMarker =
                    uiState.isAnswerSelected && !uiState.isAnswerCorrect && isCorrect

                val backgroundColor = when {
                    showCorrectMarker || showCorrectAnswerMarker -> Color(0xFF4CAF50).copy(alpha = 0.3f)
                    isWrong -> Color(0xFFF44336).copy(alpha = 0.3f)
                    isSelected -> Color(0xFF2196F3).copy(alpha = 0.2f)
                    else -> Color.Transparent
                }

                val borderColor = when {
                    showCorrectMarker || showCorrectAnswerMarker -> Color(0xFF4CAF50)
                    isWrong -> Color(0xFFF44336)
                    isSelected -> Color(0xFF2196F3)
                    else -> Color.Gray.copy(alpha = 0.5f)
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(enabled = !uiState.isAnswerSelected) {
                            vm.selectOption(index)
                        }
                        .background(
                            color = backgroundColor,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .border(
                            width = 2.dp,
                            color = borderColor,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = optionText,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        if (showCorrectMarker || showCorrectAnswerMarker) {
                            Text(
                                text = "✓",
                                color = Color(0xFF4CAF50),
                                style = MaterialTheme.typography.headlineMedium
                            )
                        } else if (isWrong) {
                            Text(
                                text = "✗",
                                color = Color(0xFFF44336),
                                style = MaterialTheme.typography.headlineMedium
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (!uiState.isAnswerSelected) {
                    Button(onClick = { vm.skipQuestion() }) {
                        Text(text = "Skip")
                    }
                } else {
                    Button(onClick = { vm.moveToNextQuestion() }) {
                        Text(text = if (uiState.isLastQuestion) "Finish" else "Next")
                    }
                }
            }
        }
    }
}
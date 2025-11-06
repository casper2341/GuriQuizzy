package com.guri.guriquizzy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.guri.guriquizzy.data.local.AppDatabase
import com.guri.guriquizzy.data.local.toEntity
import com.guri.guriquizzy.data.mapper.QuizJsonMapper
import com.guri.guriquizzy.navigation.QuestionScreen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(true) }
    var loadingText by remember { mutableStateOf("Loading questions...") }

    LaunchedEffect(Unit) {
        try {
            loadingText = "Loading questions..."
            delay(1000)

            val jsonFromAssets = context.assets.open("questions.json")
                .use { it.readBytes().toString(Charsets.UTF_8) }

            loadingText = "Parsing questions..."
            val parsed = runCatching { QuizJsonMapper.fromJson(jsonFromAssets) }.getOrNull()

            if (parsed != null) {
                loadingText = "Saving questions..."
                val dao = AppDatabase.getInstance(context).quizQuestionDao()
                val entities = parsed.map { it.toEntity() }
                dao.insertAll(entities)

                loadingText = "Ready to start!"
                delay(500)
            } else {
                loadingText = "Error loading questions"
                delay(2000)
            }
        } catch (e: Exception) {
            loadingText = "Error: ${e.message}"
            delay(2000)
        } finally {
            isLoading = false
            navController.navigate(QuestionScreen) {
                popUpTo(navController.graph.startDestinationId) { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2196F3)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = "🎯",
                style = MaterialTheme.typography.displayLarge
            )

            Text(
                text = "GuriQuizzy",
                style = MaterialTheme.typography.displayMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ),
                textAlign = TextAlign.Center
            )

            if (isLoading) {
                CircularProgressIndicator(
                    color = Color.White
                )

                Text(
                    text = loadingText,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color.White
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
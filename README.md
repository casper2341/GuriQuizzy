# 🎯 GuriQuizzy - Android Quiz App

A modern Android quiz application built with clean architecture principles, featuring interactive gameplay with streaks, auto-redirect functionality, and smooth animations.

## 📱 Features

### 🎮 Core Gameplay
- **Multiple Choice Questions**: 10 carefully crafted Android development and UX questions
- **Real-time Feedback**: Immediate visual feedback for correct/wrong answers
- **Auto-redirect**: Automatically proceeds to next question after 2 seconds (or user can manually skip)
- **Score Tracking**: Tracks correct answers and final score

### 🔥 Streak System
- **Consecutive Correct Answers**: Tracks streak of correct answers
- **Visual Streak Badge**: Appears when streak ≥ 3 with animated fire emoji
- **Streak Reset**: Resets on wrong answers or when skipping questions
- **Max Streak Tracking**: Records highest streak achieved in a session

### 🎨 User Experience
- **Beautiful UI**: Material 3 design with smooth animations
- **Progress Tracking**: Visual progress bar showing current question
- **Gesture Controls**: Swipe left to skip/next, with haptic feedback
- **Responsive Design**: Optimized for different screen sizes
- **Dark Mode Support**: Inherits system theme preferences

### 🏆 Results & Analytics
- **Detailed Results**: Shows final score, max streak, and skipped questions count

## 🛠 Tech Stack

### Core Technologies
- **Language**: Kotlin 2.0.21
- **Minimum SDK**: API 21 (Android 5.0)
- **Target SDK**: API 36 (Android 16)
- **Build System**: Gradle with Kotlin DSL

### Architecture & Design Patterns
- **Clean Architecture**: Separation of concerns with Data, Domain, and UI layers
- **MVVM Pattern**: ViewModels handle UI state and business logic
- **Repository Pattern**: Abstraction layer for data sources
- **Use Case Pattern**: Business logic encapsulation
- **Dependency Injection**: Simple DI container for testability

### Android Frameworks & Libraries

#### UI & Compose
- **Jetpack Compose**: Modern declarative UI framework
- **Navigation Compose**: Single-activity navigation
- **Lifecycle ViewModel Compose**: ViewModel integration

#### Data Persistence
- **Room Database**: SQLite abstraction with compile-time verification
- **Kotlin Serialization**: JSON parsing and serialization

#### Asynchronous Programming
- **Kotlin Coroutines**: Non-blocking operations
- **Flow**: Reactive data streams
- **StateFlow**: Observable state management

#### Development Tools
- **KAPT**: Kotlin Annotation Processing Tool (for Room)
- **Android Gradle Plugin**: Build automation
- **Compose Tooling**: UI preview and debugging

#### Future Improvements
- Adding MVI Architecture 
- Replacing hardcoding Text to string files
- Adding DI Libraries 
- Adding Gesture Controls
- Adding Animations

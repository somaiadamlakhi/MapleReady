package com.blueray.mapleready.presentation.progress

data class TopicProgressUiModel(
    val title: String,
    val progress: Float
)

data class ProgressUiState(
    val overallReadiness: Float = 0f,
    val completedTopics: Int = 0,
    val totalTopics: Int = 0,
    val topicProgress: List<TopicProgressUiModel> = emptyList()
)
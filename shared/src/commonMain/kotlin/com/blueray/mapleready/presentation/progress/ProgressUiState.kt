package com.blueray.mapleready.presentation.progress

import org.jetbrains.compose.resources.StringResource

data class TopicProgressUiModel(
    val title: StringResource,
    val progress: Float
)

data class ProgressUiState(
    val overallReadiness: Float = 0f,
    val completedTopics: Int = 0,
    val totalTopics: Int = 0,
    val topicProgress: List<TopicProgressUiModel> = emptyList()
)
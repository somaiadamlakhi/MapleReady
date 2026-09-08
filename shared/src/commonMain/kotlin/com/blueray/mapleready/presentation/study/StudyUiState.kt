package com.blueray.mapleready.presentation.study

import org.jetbrains.compose.resources.StringResource

data class StudyUiState(
    val topics: List<StudyTopicUiModel> = emptyList()
)

data class StudyTopicUiModel(
    val title: StringResource,
    val description: StringResource,
    val progress: Float
)
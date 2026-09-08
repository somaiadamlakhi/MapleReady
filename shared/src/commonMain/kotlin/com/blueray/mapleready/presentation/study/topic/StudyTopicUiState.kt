package com.blueray.mapleready.presentation.study.topic

import org.jetbrains.compose.resources.StringResource

data class StudyTopicSectionUiModel(
    val title: StringResource,
    val content: StringResource
)

data class StudyTopicUiState(
    val title: StringResource,
    val currentSectionIndex: Int = 0,
    val sections: List<StudyTopicSectionUiModel> = emptyList()
)
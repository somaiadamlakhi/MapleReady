package com.blueray.mapleready.presentation.study

import com.blueray.mapleready.domain.model.StudyTopicId
import org.jetbrains.compose.resources.StringResource

data class StudyUiState(
    val topics: List<StudyTopicUiModel> = emptyList()
)

data class StudyTopicUiModel(
    val id: StudyTopicId,
    val title: StringResource,
    val description: StringResource,
    val progress: Float
)
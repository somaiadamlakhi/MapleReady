package com.blueray.mapleready.presentation.study.topic

import com.blueray.mapleready.domain.study.model.StudyTopicId
import org.jetbrains.compose.resources.StringResource

data class StudyTopicSectionUiModel(
    val title: StringResource,
    val content: StringResource
)

data class StudyTopicUiState(
    val topicId: StudyTopicId = StudyTopicId.HISTORY,
    val title: StringResource,
    val currentSectionIndex: Int = 0,
    val sections: List<StudyTopicSectionUiModel> = emptyList()
) {

    val hasPreviousSection: Boolean
        get() = currentSectionIndex > 0

    val hasNextSection: Boolean
        get() = sections.isNotEmpty() &&
                currentSectionIndex < sections.lastIndex
}

package com.blueray.mapleready.presentation.study.topic

import com.blueray.mapleready.domain.model.StudyTopic
import com.blueray.mapleready.domain.model.StudyTopicId
import mapleready.shared.generated.resources.Res
import mapleready.shared.generated.resources.topic_history_content_1
import mapleready.shared.generated.resources.topic_history_content_2
import mapleready.shared.generated.resources.topic_history_section_1
import mapleready.shared.generated.resources.topic_history_section_2
import mapleready.shared.generated.resources.topic_history_title

fun StudyTopic.toTopicUiState(
    currentSectionIndex: Int
): StudyTopicUiState {
    return when (id) {
        StudyTopicId.HISTORY -> {
            StudyTopicUiState(
                title = Res.string.topic_history_title,
                currentSectionIndex = currentSectionIndex,
                sections = listOf(
                    StudyTopicSectionUiModel(
                        title = Res.string.topic_history_section_1,
                        content = Res.string.topic_history_content_1
                    ),
                    StudyTopicSectionUiModel(
                        title = Res.string.topic_history_section_2,
                        content = Res.string.topic_history_content_2
                    )
                )
            )
        }

        StudyTopicId.RIGHTS_AND_RESPONSIBILITIES,
        StudyTopicId.GOVERNMENT,
        StudyTopicId.ELECTIONS,
        StudyTopicId.GEOGRAPHY -> {
            StudyTopicUiState(
                title = Res.string.topic_history_title,
                currentSectionIndex = 0,
                sections = emptyList()
            )
        }
    }
}
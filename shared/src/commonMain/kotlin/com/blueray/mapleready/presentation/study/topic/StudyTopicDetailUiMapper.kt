package com.blueray.mapleready.presentation.study.topic

import com.blueray.mapleready.domain.study.model.StudyTopic
import com.blueray.mapleready.domain.study.model.StudyTopicId
import mapleready.shared.generated.resources.Res
import mapleready.shared.generated.resources.topic_elections_content_1
import mapleready.shared.generated.resources.topic_elections_content_10
import mapleready.shared.generated.resources.topic_elections_content_2
import mapleready.shared.generated.resources.topic_elections_content_3
import mapleready.shared.generated.resources.topic_elections_content_4
import mapleready.shared.generated.resources.topic_elections_content_5
import mapleready.shared.generated.resources.topic_elections_content_6
import mapleready.shared.generated.resources.topic_elections_content_7
import mapleready.shared.generated.resources.topic_elections_content_8
import mapleready.shared.generated.resources.topic_elections_content_9
import mapleready.shared.generated.resources.topic_elections_section_1
import mapleready.shared.generated.resources.topic_elections_section_10
import mapleready.shared.generated.resources.topic_elections_section_2
import mapleready.shared.generated.resources.topic_elections_section_3
import mapleready.shared.generated.resources.topic_elections_section_4
import mapleready.shared.generated.resources.topic_elections_section_5
import mapleready.shared.generated.resources.topic_elections_section_6
import mapleready.shared.generated.resources.topic_elections_section_7
import mapleready.shared.generated.resources.topic_elections_section_8
import mapleready.shared.generated.resources.topic_elections_section_9
import mapleready.shared.generated.resources.topic_elections_title
import mapleready.shared.generated.resources.topic_geography_content_1
import mapleready.shared.generated.resources.topic_geography_content_10
import mapleready.shared.generated.resources.topic_geography_content_2
import mapleready.shared.generated.resources.topic_geography_content_3
import mapleready.shared.generated.resources.topic_geography_content_4
import mapleready.shared.generated.resources.topic_geography_content_5
import mapleready.shared.generated.resources.topic_geography_content_6
import mapleready.shared.generated.resources.topic_geography_content_7
import mapleready.shared.generated.resources.topic_geography_content_8
import mapleready.shared.generated.resources.topic_geography_content_9
import mapleready.shared.generated.resources.topic_geography_section_1
import mapleready.shared.generated.resources.topic_geography_section_10
import mapleready.shared.generated.resources.topic_geography_section_2
import mapleready.shared.generated.resources.topic_geography_section_3
import mapleready.shared.generated.resources.topic_geography_section_4
import mapleready.shared.generated.resources.topic_geography_section_5
import mapleready.shared.generated.resources.topic_geography_section_6
import mapleready.shared.generated.resources.topic_geography_section_7
import mapleready.shared.generated.resources.topic_geography_section_8
import mapleready.shared.generated.resources.topic_geography_section_9
import mapleready.shared.generated.resources.topic_geography_title
import mapleready.shared.generated.resources.topic_government_content_1
import mapleready.shared.generated.resources.topic_government_content_10
import mapleready.shared.generated.resources.topic_government_content_2
import mapleready.shared.generated.resources.topic_government_content_3
import mapleready.shared.generated.resources.topic_government_content_4
import mapleready.shared.generated.resources.topic_government_content_5
import mapleready.shared.generated.resources.topic_government_content_6
import mapleready.shared.generated.resources.topic_government_content_7
import mapleready.shared.generated.resources.topic_government_content_8
import mapleready.shared.generated.resources.topic_government_content_9
import mapleready.shared.generated.resources.topic_government_section_1
import mapleready.shared.generated.resources.topic_government_section_10
import mapleready.shared.generated.resources.topic_government_section_2
import mapleready.shared.generated.resources.topic_government_section_3
import mapleready.shared.generated.resources.topic_government_section_4
import mapleready.shared.generated.resources.topic_government_section_5
import mapleready.shared.generated.resources.topic_government_section_6
import mapleready.shared.generated.resources.topic_government_section_7
import mapleready.shared.generated.resources.topic_government_section_8
import mapleready.shared.generated.resources.topic_government_section_9
import mapleready.shared.generated.resources.topic_government_title
import mapleready.shared.generated.resources.topic_history_content_1
import mapleready.shared.generated.resources.topic_history_content_10
import mapleready.shared.generated.resources.topic_history_content_2
import mapleready.shared.generated.resources.topic_history_content_3
import mapleready.shared.generated.resources.topic_history_content_4
import mapleready.shared.generated.resources.topic_history_content_5
import mapleready.shared.generated.resources.topic_history_content_6
import mapleready.shared.generated.resources.topic_history_content_7
import mapleready.shared.generated.resources.topic_history_content_8
import mapleready.shared.generated.resources.topic_history_content_9
import mapleready.shared.generated.resources.topic_history_section_1
import mapleready.shared.generated.resources.topic_history_section_10
import mapleready.shared.generated.resources.topic_history_section_2
import mapleready.shared.generated.resources.topic_history_section_3
import mapleready.shared.generated.resources.topic_history_section_4
import mapleready.shared.generated.resources.topic_history_section_5
import mapleready.shared.generated.resources.topic_history_section_6
import mapleready.shared.generated.resources.topic_history_section_7
import mapleready.shared.generated.resources.topic_history_section_8
import mapleready.shared.generated.resources.topic_history_section_9
import mapleready.shared.generated.resources.topic_history_title
import mapleready.shared.generated.resources.topic_rights_content_1
import mapleready.shared.generated.resources.topic_rights_content_10
import mapleready.shared.generated.resources.topic_rights_content_2
import mapleready.shared.generated.resources.topic_rights_content_3
import mapleready.shared.generated.resources.topic_rights_content_4
import mapleready.shared.generated.resources.topic_rights_content_5
import mapleready.shared.generated.resources.topic_rights_content_6
import mapleready.shared.generated.resources.topic_rights_content_7
import mapleready.shared.generated.resources.topic_rights_content_8
import mapleready.shared.generated.resources.topic_rights_content_9
import mapleready.shared.generated.resources.topic_rights_section_1
import mapleready.shared.generated.resources.topic_rights_section_10
import mapleready.shared.generated.resources.topic_rights_section_2
import mapleready.shared.generated.resources.topic_rights_section_3
import mapleready.shared.generated.resources.topic_rights_section_4
import mapleready.shared.generated.resources.topic_rights_section_5
import mapleready.shared.generated.resources.topic_rights_section_6
import mapleready.shared.generated.resources.topic_rights_section_7
import mapleready.shared.generated.resources.topic_rights_section_8
import mapleready.shared.generated.resources.topic_rights_section_9
import mapleready.shared.generated.resources.topic_rights_title

fun StudyTopic.toTopicUiState(
    currentSectionIndex: Int
): StudyTopicUiState {
    val topicTitle = when (id) {
        StudyTopicId.HISTORY -> Res.string.topic_history_title
        StudyTopicId.RIGHTS_AND_RESPONSIBILITIES -> Res.string.topic_rights_title
        StudyTopicId.GOVERNMENT -> Res.string.topic_government_title
        StudyTopicId.ELECTIONS -> Res.string.topic_elections_title
        StudyTopicId.GEOGRAPHY -> Res.string.topic_geography_title
    }

    val mappedSections = when (id) {
        StudyTopicId.HISTORY -> listOf(
            StudyTopicSectionUiModel(Res.string.topic_history_section_1, Res.string.topic_history_content_1),
            StudyTopicSectionUiModel(Res.string.topic_history_section_2, Res.string.topic_history_content_2),
            StudyTopicSectionUiModel(Res.string.topic_history_section_3, Res.string.topic_history_content_3),
            StudyTopicSectionUiModel(Res.string.topic_history_section_4, Res.string.topic_history_content_4),
            StudyTopicSectionUiModel(Res.string.topic_history_section_5, Res.string.topic_history_content_5),
            StudyTopicSectionUiModel(Res.string.topic_history_section_6, Res.string.topic_history_content_6),
            StudyTopicSectionUiModel(Res.string.topic_history_section_7, Res.string.topic_history_content_7),
            StudyTopicSectionUiModel(Res.string.topic_history_section_8, Res.string.topic_history_content_8),
            StudyTopicSectionUiModel(Res.string.topic_history_section_9, Res.string.topic_history_content_9),
            StudyTopicSectionUiModel(Res.string.topic_history_section_10, Res.string.topic_history_content_10)
        )
        StudyTopicId.RIGHTS_AND_RESPONSIBILITIES -> listOf(
            StudyTopicSectionUiModel(Res.string.topic_rights_section_1, Res.string.topic_rights_content_1),
            StudyTopicSectionUiModel(Res.string.topic_rights_section_2, Res.string.topic_rights_content_2),
            StudyTopicSectionUiModel(Res.string.topic_rights_section_3, Res.string.topic_rights_content_3),
            StudyTopicSectionUiModel(Res.string.topic_rights_section_4, Res.string.topic_rights_content_4),
            StudyTopicSectionUiModel(Res.string.topic_rights_section_5, Res.string.topic_rights_content_5),
            StudyTopicSectionUiModel(Res.string.topic_rights_section_6, Res.string.topic_rights_content_6),
            StudyTopicSectionUiModel(Res.string.topic_rights_section_7, Res.string.topic_rights_content_7),
            StudyTopicSectionUiModel(Res.string.topic_rights_section_8, Res.string.topic_rights_content_8),
            StudyTopicSectionUiModel(Res.string.topic_rights_section_9, Res.string.topic_rights_content_9),
            StudyTopicSectionUiModel(Res.string.topic_rights_section_10, Res.string.topic_rights_content_10)
        )
        StudyTopicId.GOVERNMENT -> listOf(
            StudyTopicSectionUiModel(Res.string.topic_government_section_1, Res.string.topic_government_content_1),
            StudyTopicSectionUiModel(Res.string.topic_government_section_2, Res.string.topic_government_content_2),
            StudyTopicSectionUiModel(Res.string.topic_government_section_3, Res.string.topic_government_content_3),
            StudyTopicSectionUiModel(Res.string.topic_government_section_4, Res.string.topic_government_content_4),
            StudyTopicSectionUiModel(Res.string.topic_government_section_5, Res.string.topic_government_content_5),
            StudyTopicSectionUiModel(Res.string.topic_government_section_6, Res.string.topic_government_content_6),
            StudyTopicSectionUiModel(Res.string.topic_government_section_7, Res.string.topic_government_content_7),
            StudyTopicSectionUiModel(Res.string.topic_government_section_8, Res.string.topic_government_content_8),
            StudyTopicSectionUiModel(Res.string.topic_government_section_9, Res.string.topic_government_content_9),
            StudyTopicSectionUiModel(Res.string.topic_government_section_10, Res.string.topic_government_content_10)
        )
        StudyTopicId.ELECTIONS -> listOf(
            StudyTopicSectionUiModel(Res.string.topic_elections_section_1, Res.string.topic_elections_content_1),
            StudyTopicSectionUiModel(Res.string.topic_elections_section_2, Res.string.topic_elections_content_2),
            StudyTopicSectionUiModel(Res.string.topic_elections_section_3, Res.string.topic_elections_content_3),
            StudyTopicSectionUiModel(Res.string.topic_elections_section_4, Res.string.topic_elections_content_4),
            StudyTopicSectionUiModel(Res.string.topic_elections_section_5, Res.string.topic_elections_content_5),
            StudyTopicSectionUiModel(Res.string.topic_elections_section_6, Res.string.topic_elections_content_6),
            StudyTopicSectionUiModel(Res.string.topic_elections_section_7, Res.string.topic_elections_content_7),
            StudyTopicSectionUiModel(Res.string.topic_elections_section_8, Res.string.topic_elections_content_8),
            StudyTopicSectionUiModel(Res.string.topic_elections_section_9, Res.string.topic_elections_content_9),
            StudyTopicSectionUiModel(Res.string.topic_elections_section_10, Res.string.topic_elections_content_10)
        )
        StudyTopicId.GEOGRAPHY -> listOf(
            StudyTopicSectionUiModel(Res.string.topic_geography_section_1, Res.string.topic_geography_content_1),
            StudyTopicSectionUiModel(Res.string.topic_geography_section_2, Res.string.topic_geography_content_2),
            StudyTopicSectionUiModel(Res.string.topic_geography_section_3, Res.string.topic_geography_content_3),
            StudyTopicSectionUiModel(Res.string.topic_geography_section_4, Res.string.topic_geography_content_4),
            StudyTopicSectionUiModel(Res.string.topic_geography_section_5, Res.string.topic_geography_content_5),
            StudyTopicSectionUiModel(Res.string.topic_geography_section_6, Res.string.topic_geography_content_6),
            StudyTopicSectionUiModel(Res.string.topic_geography_section_7, Res.string.topic_geography_content_7),
            StudyTopicSectionUiModel(Res.string.topic_geography_section_8, Res.string.topic_geography_content_8),
            StudyTopicSectionUiModel(Res.string.topic_geography_section_9, Res.string.topic_geography_content_9),
            StudyTopicSectionUiModel(Res.string.topic_geography_section_10, Res.string.topic_geography_content_10)
        )
    }

    return StudyTopicUiState(
        topicId = id,
        title = topicTitle,
        currentSectionIndex = currentSectionIndex,
        sections = mappedSections
    )
}
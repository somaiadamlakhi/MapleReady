package com.blueray.mapleready.presentation.study

import com.blueray.mapleready.domain.model.StudyTopic
import com.blueray.mapleready.domain.model.StudyTopicId
import mapleready.shared.generated.resources.Res
import mapleready.shared.generated.resources.topic_elections_description
import mapleready.shared.generated.resources.topic_elections_title
import mapleready.shared.generated.resources.topic_geography_description
import mapleready.shared.generated.resources.topic_geography_title
import mapleready.shared.generated.resources.topic_government_description
import mapleready.shared.generated.resources.topic_government_title
import mapleready.shared.generated.resources.topic_history_description
import mapleready.shared.generated.resources.topic_history_title
import mapleready.shared.generated.resources.topic_rights_description
import mapleready.shared.generated.resources.topic_rights_title

fun StudyTopic.toUiModel(): StudyTopicUiModel {
    val resources = when (id) {
        StudyTopicId.HISTORY -> {
            TopicResources(
                title = Res.string.topic_history_title,
                description = Res.string.topic_history_description
            )
        }

        StudyTopicId.RIGHTS_AND_RESPONSIBILITIES -> {
            TopicResources(
                title = Res.string.topic_rights_title,
                description = Res.string.topic_rights_description
            )
        }

        StudyTopicId.GOVERNMENT -> {
            TopicResources(
                title = Res.string.topic_government_title,
                description = Res.string.topic_government_description
            )
        }

        StudyTopicId.ELECTIONS -> {
            TopicResources(
                title = Res.string.topic_elections_title,
                description = Res.string.topic_elections_description
            )
        }

        StudyTopicId.GEOGRAPHY -> {
            TopicResources(
                title = Res.string.topic_geography_title,
                description = Res.string.topic_geography_description
            )
        }
    }

    return StudyTopicUiModel(
        id = id,
        title = resources.title,
        description = resources.description,
        progress = progress
    )
}

private data class TopicResources(
    val title: org.jetbrains.compose.resources.StringResource,
    val description: org.jetbrains.compose.resources.StringResource
)
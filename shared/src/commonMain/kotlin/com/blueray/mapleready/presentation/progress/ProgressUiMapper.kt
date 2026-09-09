package com.blueray.mapleready.presentation.progress

import com.blueray.mapleready.domain.model.StudyTopic
import com.blueray.mapleready.domain.model.StudyTopicId
import mapleready.shared.generated.resources.Res
import mapleready.shared.generated.resources.topic_elections_title
import mapleready.shared.generated.resources.topic_geography_title
import mapleready.shared.generated.resources.topic_government_title
import mapleready.shared.generated.resources.topic_history_title
import mapleready.shared.generated.resources.topic_rights_title

fun List<StudyTopic>.toProgressUiState(): ProgressUiState {

    val overallReadiness = if (isEmpty()) {
        0f
    } else {
        map { it.progress }.average().toFloat()
    }

    val completedTopics = count {
        it.progress >= 1f
    }

    return ProgressUiState(
        overallReadiness = overallReadiness,
        completedTopics = completedTopics,
        totalTopics = size,
        topicProgress = map { topic ->
            TopicProgressUiModel(
                title = topic.id.toTitleResource(),
                progress = topic.progress
            )
        }
    )
}

private fun StudyTopicId.toTitleResource() =
    when (this) {
        StudyTopicId.HISTORY ->
            Res.string.topic_history_title

        StudyTopicId.RIGHTS_AND_RESPONSIBILITIES ->
            Res.string.topic_rights_title

        StudyTopicId.GOVERNMENT ->
            Res.string.topic_government_title

        StudyTopicId.ELECTIONS ->
            Res.string.topic_elections_title

        StudyTopicId.GEOGRAPHY ->
            Res.string.topic_geography_title
    }
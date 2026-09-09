package com.blueray.mapleready.presentation.progress

import com.blueray.mapleready.domain.study.model.StudyTopic
import com.blueray.mapleready.domain.study.model.StudyTopicId
import com.blueray.mapleready.domain.study.StudyRepository
import com.blueray.mapleready.domain.study.calculateProgress
import mapleready.shared.generated.resources.Res
import mapleready.shared.generated.resources.topic_elections_title
import mapleready.shared.generated.resources.topic_geography_title
import mapleready.shared.generated.resources.topic_government_title
import mapleready.shared.generated.resources.topic_history_title
import mapleready.shared.generated.resources.topic_rights_title

fun List<StudyTopic>.toProgressUiState(
    repository: StudyRepository
): ProgressUiState {

    val topicProgress = map { topic ->

        val completedSections =
            repository.getCompletedSectionIndexes(
                topicId = topic.id
            )

        val progress = topic.calculateProgress(
            completedSectionIndexes = completedSections
        )

        TopicProgressUiModel(
            title = topic.id.toTitleResource(),
            progress = progress
        )
    }

    val overallReadiness = if (topicProgress.isEmpty()) {
        0f
    } else {
        topicProgress
            .map { it.progress }
            .average()
            .toFloat()
    }

    val completedTopics = topicProgress.count {
        it.progress >= 1f
    }

    return ProgressUiState(
        overallReadiness = overallReadiness,
        completedTopics = completedTopics,
        totalTopics = topicProgress.size,
        topicProgress = topicProgress
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
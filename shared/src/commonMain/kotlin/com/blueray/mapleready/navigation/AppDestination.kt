package com.blueray.mapleready.navigation

import com.blueray.mapleready.domain.model.StudyTopicId

sealed interface AppDestination {

    data object Home : AppDestination

    data object Study : AppDestination

    data object Progress : AppDestination

    data class StudyTopic(
        val topicId: StudyTopicId
    ) : AppDestination
}
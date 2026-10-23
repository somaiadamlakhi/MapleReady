package com.blueray.mapleready.navigation

import com.blueray.mapleready.domain.study.model.StudyTopicId

sealed class AppDestination {

    data object Home : AppDestination()

    data object Study : AppDestination()

    data object Progress : AppDestination()

    data object Practice : AppDestination()

    data object MockTest : AppDestination()

    data class StudyTopic(
        val topicId: StudyTopicId
    ) : AppDestination()
}

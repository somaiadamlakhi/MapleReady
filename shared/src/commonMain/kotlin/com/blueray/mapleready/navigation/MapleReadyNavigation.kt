package com.blueray.mapleready.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.blueray.mapleready.data.study.StudyRepositoryImpl
import com.blueray.mapleready.presentation.components.BottomNavItem
import com.blueray.mapleready.presentation.home.HomeScreen
import com.blueray.mapleready.presentation.home.HomeUiState
import com.blueray.mapleready.presentation.progress.ProgressScreen
import com.blueray.mapleready.presentation.progress.toProgressUiState
import com.blueray.mapleready.presentation.study.StudyScreen
import com.blueray.mapleready.presentation.study.StudyUiState
import com.blueray.mapleready.presentation.study.toUiModel
import com.blueray.mapleready.presentation.study.topic.StudyTopicScreen
import com.blueray.mapleready.presentation.study.topic.toTopicUiState

@Composable
fun MapleReadyNavigation() {

    var currentSectionIndex by remember {
        mutableStateOf(0)
    }

    var destination by remember {
        mutableStateOf<AppDestination>(
            AppDestination.Home
        )
    }

    val studyRepository = remember {
        StudyRepositoryImpl()
    }
    when (val current = destination) {

        AppDestination.Home -> {
            HomeScreen(
                state = HomeUiState(
                    readiness = 0.72f
                ),
                onStudyClick = {
                    destination = AppDestination.Study
                },
                onPracticeClick = {
                    // later
                },
                onMockTestClick = {
                    // later
                },
                onBottomNavItemSelected = { item ->
                    destination = item.toDestination()
                }
            )
        }

        AppDestination.Study -> {

            val topics = studyRepository
                .getTopics()
                .map { topic ->
                    topic.toUiModel()
                }

            StudyScreen(
                state = StudyUiState(
                    topics = topics
                ),
                onTopicClick = { topic ->
                    currentSectionIndex = 0

                    destination = AppDestination.StudyTopic(
                        topicId = topic.id
                    )
                },
                onBottomNavItemSelected = { item ->
                    destination = item.toDestination()
                }
            )
        }

        AppDestination.Progress -> {

            val progressState = studyRepository
                .getTopics()
                .toProgressUiState()

            ProgressScreen(
                state = progressState,
                onBottomNavItemSelected = { item ->
                    destination = item.toDestination()
                }
            )
        }

        is AppDestination.StudyTopic -> {

            val topic = studyRepository.getTopicById(
                current.topicId
            )

            if (topic != null) {

                val topicState = topic.toTopicUiState(
                    currentSectionIndex = currentSectionIndex
                )

                StudyTopicScreen(
                    state = topicState,

                    onNextSectionClick = {
                        if (currentSectionIndex < topicState.sections.lastIndex) {
                            currentSectionIndex++
                        }
                    },

                    onPreviousSectionClick = {
                        if (currentSectionIndex > 0) {
                            currentSectionIndex--
                        }
                    },

                    onBackClick = {
                        destination = AppDestination.Study
                    }
                )
            }
        }
    }
}

private fun BottomNavItem.toDestination(): AppDestination {
    return when (this) {
        BottomNavItem.HOME -> AppDestination.Home
        BottomNavItem.STUDY -> AppDestination.Study
        BottomNavItem.PROGRESS -> AppDestination.Progress
    }
}
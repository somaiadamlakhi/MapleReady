package com.blueray.mapleready.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.blueray.mapleready.domain.model.StudyTopicId
import com.blueray.mapleready.presentation.components.BottomNavItem
import com.blueray.mapleready.presentation.home.HomeScreen
import com.blueray.mapleready.presentation.home.HomeUiState
import com.blueray.mapleready.presentation.study.StudyScreen
import com.blueray.mapleready.presentation.study.StudyTopicUiModel
import com.blueray.mapleready.presentation.study.StudyUiState
import com.blueray.mapleready.presentation.study.topic.StudyTopicScreen
import com.blueray.mapleready.presentation.study.topic.StudyTopicSectionUiModel
import com.blueray.mapleready.presentation.study.topic.StudyTopicUiState
import mapleready.shared.generated.resources.Res
import mapleready.shared.generated.resources.topic_elections_description
import mapleready.shared.generated.resources.topic_elections_title
import mapleready.shared.generated.resources.topic_geography_description
import mapleready.shared.generated.resources.topic_geography_title
import mapleready.shared.generated.resources.topic_government_description
import mapleready.shared.generated.resources.topic_government_title
import mapleready.shared.generated.resources.topic_history_content_1
import mapleready.shared.generated.resources.topic_history_content_2
import mapleready.shared.generated.resources.topic_history_description
import mapleready.shared.generated.resources.topic_history_section_1
import mapleready.shared.generated.resources.topic_history_section_2
import mapleready.shared.generated.resources.topic_history_title
import mapleready.shared.generated.resources.topic_rights_description
import mapleready.shared.generated.resources.topic_rights_title

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
            StudyScreen(
                state = previewStudyState(),
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
            // We'll build ProgressScreen next.
        }

        is AppDestination.StudyTopic -> {

            val topicState = studyTopicState(
                topicId = current.topicId
            ).copy(
                currentSectionIndex = currentSectionIndex
            )

            StudyTopicScreen(
                state = topicState,

                onNextSectionClick = {
                    if (currentSectionIndex < topicState.sections.lastIndex) {
                        currentSectionIndex++
                    }
                },

                onBackClick = {
                    destination = AppDestination.Study
                }
            )
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

private fun previewStudyState(): StudyUiState {
    return StudyUiState(
        topics = listOf(
            StudyTopicUiModel(
                id = StudyTopicId.HISTORY,
                title = Res.string.topic_history_title,
                description = Res.string.topic_history_description,
                progress = 0.65f
            ),
            StudyTopicUiModel(
                id = StudyTopicId.RIGHTS_AND_RESPONSIBILITIES,
                title = Res.string.topic_rights_title,
                description = Res.string.topic_rights_description,
                progress = 0.40f
            ),
            StudyTopicUiModel(
                id = StudyTopicId.GOVERNMENT,
                title = Res.string.topic_government_title,
                description = Res.string.topic_government_description,
                progress = 0.80f
            ),
            StudyTopicUiModel(
                id = StudyTopicId.ELECTIONS,
                title = Res.string.topic_elections_title,
                description = Res.string.topic_elections_description,
                progress = 0.25f
            ),
            StudyTopicUiModel(
                id = StudyTopicId.GEOGRAPHY,
                title = Res.string.topic_geography_title,
                description = Res.string.topic_geography_description,
                progress = 0.50f
            )
        )
    )
}


private fun studyTopicState(
    topicId: StudyTopicId
): StudyTopicUiState {

    return when (topicId) {

        StudyTopicId.HISTORY -> {
            StudyTopicUiState(
                title = Res.string.topic_history_title,
                currentSectionIndex = 0,
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

        StudyTopicId.RIGHTS_AND_RESPONSIBILITIES -> {
            TODO("Rights topic")
        }

        StudyTopicId.GOVERNMENT -> {
            TODO("Government topic")
        }

        StudyTopicId.ELECTIONS -> {
            TODO("Elections topic")
        }

        StudyTopicId.GEOGRAPHY -> {
            TODO("Geography topic")
        }
    }
}
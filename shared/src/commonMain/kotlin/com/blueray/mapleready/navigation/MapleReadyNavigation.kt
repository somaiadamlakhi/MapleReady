package com.blueray.mapleready.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.blueray.mapleready.di.ViewModelProvider
import com.blueray.mapleready.presentation.components.BottomNavItem
import com.blueray.mapleready.presentation.home.HomeScreen
import com.blueray.mapleready.presentation.home.HomeUiState
import com.blueray.mapleready.presentation.progress.ProgressScreen
import com.blueray.mapleready.presentation.study.StudyScreen
import com.blueray.mapleready.presentation.study.topic.StudyTopicScreen

@Composable
fun MapleReadyNavigation(
    viewModelProvider: ViewModelProvider
) {

    var destination by remember {
        mutableStateOf<AppDestination>(
            AppDestination.Home
        )
    }

    val studyViewModel = remember {
        viewModelProvider.provideStudyViewModel()
    }

    val progressViewModel = remember {
        viewModelProvider.provideProgressViewModel()
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

            val state by studyViewModel.uiState.collectAsState()

            StudyScreen(
                state = state,
                onTopicClick = { topic ->
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

            val state by progressViewModel.uiState.collectAsState()

            ProgressScreen(
                state = state,
                onBottomNavItemSelected = { item ->
                    destination = item.toDestination()
                }
            )
        }

        is AppDestination.StudyTopic -> {

            val studyTopicViewModel = remember(current.topicId) {
                viewModelProvider.provideStudyTopicViewModel(
                    topicId = current.topicId
                )
            }

            val state by studyTopicViewModel.uiState.collectAsState()

            state?.let { topicState ->
                StudyTopicScreen(
                    state = topicState,
                    onNextSectionClick = {
                        studyTopicViewModel.nextSection()
                    },
                    onPreviousSectionClick = {
                        studyTopicViewModel.previousSection()
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
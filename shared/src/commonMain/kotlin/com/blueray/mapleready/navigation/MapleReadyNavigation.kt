package com.blueray.mapleready.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.blueray.mapleready.di.ViewModelProvider
import com.blueray.mapleready.domain.study.model.StudyTopicId
import com.blueray.mapleready.presentation.components.BottomNavItem
import com.blueray.mapleready.presentation.home.HomeScreen
import com.blueray.mapleready.presentation.practice.PracticeScreen
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

    val homeViewModel = remember {
        viewModelProvider.provideHomeViewModel()
    }

    val practiceViewModel = remember {
        viewModelProvider.providePracticeViewModel()
    }

    when (val current = destination) {

        AppDestination.Home -> {

            LaunchedEffect(Unit) {
                homeViewModel.refresh()
            }

            val state by homeViewModel.uiState.collectAsState()

            HomeScreen(
                state = state,
                onStudyClick = {
                    destination = AppDestination.Study
                },
                onPracticeClick = {
                    destination = AppDestination.Practice
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

            LaunchedEffect(Unit) {
                progressViewModel.refresh()
            }

            val state by progressViewModel.uiState.collectAsState()

            ProgressScreen(
                state = state,
                onBottomNavItemSelected = { item ->
                    destination = item.toDestination()
                }
            )
        }

        AppDestination.Practice -> {

            LaunchedEffect(Unit) {
                practiceViewModel.loadQuestions(
                    topicId = StudyTopicId.HISTORY
                )
            }

            val state by practiceViewModel.uiState.collectAsState()

            PracticeScreen(
                state = state,
                onAnswerClick = { answerId ->
                    practiceViewModel.selectAnswer(answerId)
                },
                onCheckAnswerClick = {
                    practiceViewModel.checkAnswer()
                },
                onNextQuestionClick = {
                    practiceViewModel.nextQuestion()
                },
                onBackClick = {
                    destination = AppDestination.Home
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
                        studyViewModel.refresh()
                        destination = AppDestination.Study
                    },
                    onCompleteSectionClick = {
                        studyTopicViewModel.markCurrentSectionCompleted()
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
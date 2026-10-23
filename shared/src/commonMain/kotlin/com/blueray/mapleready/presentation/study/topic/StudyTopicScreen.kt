package com.blueray.mapleready.presentation.study.topic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blueray.mapleready.domain.arabic.ArabicExplanationHelper
import com.blueray.mapleready.presentation.components.ArabicExplanationButton
import com.blueray.mapleready.presentation.components.ArabicExplanationModal
import com.blueray.mapleready.presentation.theme.MapleReadyTheme
import com.blueray.mapleready.presentation.theme.PracticeBackground
import com.blueray.mapleready.presentation.theme.ProgressTrack
import mapleready.shared.generated.resources.Res
import mapleready.shared.generated.resources.complete
import mapleready.shared.generated.resources.complete_and_next
import mapleready.shared.generated.resources.previous_section
import mapleready.shared.generated.resources.section_progress
import mapleready.shared.generated.resources.topic_history_content_1
import mapleready.shared.generated.resources.topic_history_content_2
import mapleready.shared.generated.resources.topic_history_section_1
import mapleready.shared.generated.resources.topic_history_section_2
import mapleready.shared.generated.resources.topic_history_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun StudyTopicScreen(
    state: StudyTopicUiState,
    onNextSectionClick: () -> Unit,
    onPreviousSectionClick: () -> Unit,
    onCompleteSectionClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold { innerPadding ->

        StudyTopicContent(
            state = state,
            onNextSectionClick = onNextSectionClick,
            onPreviousSectionClick = onPreviousSectionClick,
            onCompleteSectionClick = onCompleteSectionClick,
            onBackClick = onBackClick,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun StudyTopicContent(
    state: StudyTopicUiState,
    onNextSectionClick: () -> Unit,
    onPreviousSectionClick: () -> Unit,
    onCompleteSectionClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val hasSections = state.sections.isNotEmpty()

    val safeIndex = if (hasSections) {
        state.currentSectionIndex.coerceIn(
            minimumValue = 0,
            maximumValue = state.sections.lastIndex
        )
    } else {
        0
    }

    val currentSection =
        state.sections.getOrNull(safeIndex)

    val progress = if (hasSections) {
        (safeIndex + 1f) / state.sections.size
    } else {
        0f
    }

    val isFirstSection =
        safeIndex == 0

    val isLastSection =
        hasSections &&
                safeIndex == state.sections.lastIndex

    var showArabicExplanation by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.background
            )
            .verticalScroll(
                rememberScrollState()
            )
            .padding(
                horizontal = 24.dp,
                vertical = 12.dp
            ),
        verticalArrangement =
            Arrangement.spacedBy(16.dp)
    ) {

        // Back button + topic title
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    imageVector =
                        Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint =
                        MaterialTheme.colorScheme.primary
                )
            }

            Text(
                text = stringResource(
                    state.title
                ),
                style =
                    MaterialTheme.typography.headlineLarge,
                color =
                    MaterialTheme.colorScheme.primary
            )
        }

        // Show the section UI only when sections exist
        if (hasSections) {

            Text(
                text = stringResource(
                    Res.string.section_progress,
                    safeIndex + 1,
                    state.sections.size
                ),
                style =
                    MaterialTheme.typography.labelLarge,
                color =
                    MaterialTheme.colorScheme.onBackground
                        .copy(alpha = 0.7f)
            )

            LinearProgressIndicator(
                progress = {
                    progress
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color =
                    MaterialTheme.colorScheme.primary,
                trackColor = ProgressTrack
            )

            currentSection?.let { section ->

                Card(
                    modifier =
                        Modifier.fillMaxWidth(),
                    shape =
                        RoundedCornerShape(20.dp),
                    colors =
                        CardDefaults.cardColors(
                            containerColor =
                                PracticeBackground.copy(
                                    alpha = 0.55f
                                )
                        )
                ) {

                    Column(
                        modifier =
                            Modifier.padding(20.dp),
                        verticalArrangement =
                            Arrangement.spacedBy(12.dp)
                    ) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(
                                    section.title
                                ),
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.weight(1f)
                            )

                            ArabicExplanationButton(
                                onClick = { showArabicExplanation = true }
                            )
                        }

                        Text(
                            text = stringResource(
                                section.content
                            ),
                            style =
                                MaterialTheme.typography
                                    .bodyLarge,
                            color =
                                MaterialTheme.colorScheme
                                    .onSurface
                                    .copy(alpha = 0.82f)
                        )
                    }
                }
            }

            if (showArabicExplanation && currentSection != null) {
                val explanation = ArabicExplanationHelper.getStudySectionExplanation(
                    topicId = state.topicId,
                    sectionIndex = safeIndex,
                    sectionTitle = stringResource(currentSection.title),
                    sectionContent = stringResource(currentSection.content)
                )

                ArabicExplanationModal(
                    explanationText = explanation,
                    onDismiss = { showArabicExplanation = false }
                )
            }

            Spacer(
                modifier =
                    Modifier.height(8.dp)
            )

            Row(
                modifier =
                    Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.spacedBy(12.dp)
            ) {

                if (!isFirstSection) {

                    OutlinedButton(
                        onClick =
                            onPreviousSectionClick,
                        modifier =
                            Modifier.weight(1f)
                    ) {
                        Text(
                            text = stringResource(
                                Res.string.previous_section
                            )
                        )
                    }
                }

                Button(
                    onClick = {

                        onCompleteSectionClick()

                        if (isLastSection) {
                            onBackClick()
                        } else {
                            onNextSectionClick()
                        }
                    },
                    modifier =
                        Modifier.weight(1f),
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor =
                                MaterialTheme.colorScheme.primary
                        )
                ) {
                    Text(
                        text = stringResource(
                            if (isLastSection) {
                                Res.string.complete
                            } else {
                                Res.string.complete_and_next
                            }
                        )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun StudyTopicScreenPreview() {

    MapleReadyTheme {

        StudyTopicScreen(
            state = StudyTopicUiState(
                title =
                    Res.string.topic_history_title,
                currentSectionIndex = 0,
                sections = listOf(
                    StudyTopicSectionUiModel(
                        title =
                            Res.string.topic_history_section_1,
                        content =
                            Res.string.topic_history_content_1
                    ),
                    StudyTopicSectionUiModel(
                        title =
                            Res.string.topic_history_section_2,
                        content =
                            Res.string.topic_history_content_2
                    )
                )
            ),
            onNextSectionClick = {},
            onPreviousSectionClick = {},
            onCompleteSectionClick = {},
            onBackClick = {}
        )
    }
}
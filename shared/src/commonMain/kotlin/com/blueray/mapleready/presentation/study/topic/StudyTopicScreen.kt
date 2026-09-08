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
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blueray.mapleready.presentation.theme.MapleReadyTheme
import com.blueray.mapleready.presentation.theme.PracticeBackground
import com.blueray.mapleready.presentation.theme.ProgressTrack
import mapleready.shared.generated.resources.Res
import mapleready.shared.generated.resources.next_section
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
    onNextSectionClick: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(state.title)
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
    }
}

@Composable
private fun StudyTopicContent(
    state: StudyTopicUiState,
    onNextSectionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val safeIndex = state.currentSectionIndex
        .coerceIn(
            0,
            (state.sections.size - 1).coerceAtLeast(0)
        )

    val currentSection = state.sections.getOrNull(safeIndex)

    val progress = if (state.sections.isNotEmpty()) {
        (safeIndex + 1f) / state.sections.size
    } else {
        0f
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = stringResource(state.title),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(
                    Res.string.section_progress,
                    safeIndex + 1,
                    state.sections.size
                ),
                style = MaterialTheme.typography.labelLarge
            )
        }

        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            color = MaterialTheme.colorScheme.primary,
            trackColor = ProgressTrack
        )

        currentSection?.let { section ->

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = PracticeBackground.copy(
                        alpha = 0.55f
                    )
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = stringResource(section.title),
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(
                        text = stringResource(section.content),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface.copy(
                            alpha = 0.82f
                        )
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Button(
            onClick = onNextSectionClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                text = stringResource(
                    Res.string.next_section
                )
            )
        }
    }
}

@Preview
@Composable
private fun StudyTopicScreenPreview() {
    MapleReadyTheme {
        StudyTopicScreen(
            state = StudyTopicUiState(
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
            ),
            onNextSectionClick = {},
            onBackClick = {}
        )
    }
}
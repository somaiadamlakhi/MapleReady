package com.blueray.mapleready.presentation.study

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blueray.mapleready.domain.study.model.StudyTopicId
import com.blueray.mapleready.presentation.components.BottomNavItem
import com.blueray.mapleready.presentation.components.MapleReadyBottomBar
import com.blueray.mapleready.presentation.theme.MapleReadyTheme
import com.blueray.mapleready.presentation.theme.PracticeBackground
import com.blueray.mapleready.presentation.theme.ProgressTrack
import mapleready.shared.generated.resources.Res
import mapleready.shared.generated.resources.percentage
import mapleready.shared.generated.resources.study_screen_subtitle
import mapleready.shared.generated.resources.study_screen_title
import mapleready.shared.generated.resources.topic_elections_description
import mapleready.shared.generated.resources.topic_elections_title
import mapleready.shared.generated.resources.topic_geography_description
import mapleready.shared.generated.resources.topic_geography_title
import mapleready.shared.generated.resources.topic_government_description
import mapleready.shared.generated.resources.topic_government_title
import mapleready.shared.generated.resources.topic_history_description
import mapleready.shared.generated.resources.topic_history_title
import mapleready.shared.generated.resources.topic_rights_description
import mapleready.shared.generated.resources.topic_rights_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun StudyScreen(
    state: StudyUiState,
    onTopicClick: (StudyTopicUiModel) -> Unit = {},
    onBottomNavItemSelected: (BottomNavItem) -> Unit = {}
) {
    Scaffold(
        bottomBar = {
            MapleReadyBottomBar(
                selectedItem = BottomNavItem.STUDY,
                onItemSelected = onBottomNavItemSelected
            )
        }
    ) { innerPadding ->

        StudyContent(
            state = state,
            onTopicClick = onTopicClick,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun StudyContent(
    state: StudyUiState,
    onTopicClick: (StudyTopicUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        item {
            Spacer(
                modifier = Modifier.height(24.dp)
            )

            Text(
                text = stringResource(Res.string.study_screen_title),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = stringResource(Res.string.study_screen_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(
                    alpha = 0.7f
                )
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )
        }

        items(state.topics) { topic ->
            StudyTopicCard(
                topic = topic,
                onClick = {
                    onTopicClick(topic)
                }
            )
        }

        item {
            Spacer(
                modifier = Modifier.height(24.dp)
            )
        }
    }
}

@Composable
private fun StudyTopicCard(
    topic: StudyTopicUiModel,
    onClick: () -> Unit
) {
    val safeProgress = topic.progress.coerceIn(0f, 1f)
    val percentage = (safeProgress * 100).toInt()

    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = PracticeBackground.copy(
                alpha = 0.55f
            )
        )
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = stringResource(topic.title),
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text = stringResource(topic.description),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(
                            alpha = 0.7f
                        )
                    )
                }

                Text(
                    text = stringResource(
                        Res.string.percentage,
                        percentage
                    ),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            LinearProgressIndicator(
                progress = { safeProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = ProgressTrack
            )
        }
    }
}

@Preview
@Composable
private fun StudyScreenPreview() {
    MapleReadyTheme {
        StudyScreen(
            state = StudyUiState(
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
            ),
            onTopicClick = {},
            onBottomNavItemSelected = {}
        )
    }
}
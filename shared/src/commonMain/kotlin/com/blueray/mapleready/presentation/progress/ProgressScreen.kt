package com.blueray.mapleready.presentation.progress

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
import com.blueray.mapleready.presentation.components.BottomNavItem
import com.blueray.mapleready.presentation.components.MapleReadyBottomBar
import com.blueray.mapleready.presentation.theme.MapleReadyTheme
import com.blueray.mapleready.presentation.theme.PracticeBackground
import com.blueray.mapleready.presentation.theme.ProgressTrack
import mapleready.shared.generated.resources.Res
import mapleready.shared.generated.resources.progress_screen_title
import mapleready.shared.generated.resources.progress_screen_subtitle
import mapleready.shared.generated.resources.topic_elections_title
import mapleready.shared.generated.resources.topic_geography_title
import mapleready.shared.generated.resources.topic_government_title
import mapleready.shared.generated.resources.topic_history_title
import mapleready.shared.generated.resources.topic_rights_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun ProgressScreen(
    state: ProgressUiState,
    onBottomNavItemSelected: (BottomNavItem) -> Unit = {}
) {
    Scaffold(
        bottomBar = {
            MapleReadyBottomBar(
                selectedItem = BottomNavItem.PROGRESS,
                onItemSelected = onBottomNavItemSelected
            )
        }
    ) { innerPadding ->

        ProgressContent(
            state = state,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun ProgressContent(
    state: ProgressUiState,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {
            Spacer(Modifier.height(24.dp))

            Text(
                text = stringResource(Res.string.progress_screen_title),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = stringResource(Res.string.progress_screen_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )

            Spacer(Modifier.height(18.dp))

            OverallReadinessCard(state)
        }

        items(state.topicProgress) { topic ->
            TopicProgressCard(topic)
        }

        item {
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun OverallReadinessCard(
    state: ProgressUiState
) {
    val safeProgress = state.overallReadiness.coerceIn(0f, 1f)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = PracticeBackground.copy(alpha = 0.55f)
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Overall Readiness",
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "${(safeProgress * 100).toInt()}%",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            LinearProgressIndicator(
                progress = { safeProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = ProgressTrack
            )

            Text(
                text = "${state.completedTopics} of ${state.totalTopics} topics completed",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
private fun TopicProgressCard(
    topic: TopicProgressUiModel
) {
    val safeProgress = topic.progress.coerceIn(0f, 1f)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(topic.title),
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "${(safeProgress * 100).toInt()}%",
                    color = MaterialTheme.colorScheme.primary
                )
            }

            LinearProgressIndicator(
                progress = { safeProgress },
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary,
                trackColor = ProgressTrack
            )
        }
    }
}

@Preview
@Composable
private fun ProgressScreenPreview() {
    MapleReadyTheme {
        ProgressScreen(
            state = ProgressUiState(
                overallReadiness = 0.72f,
                completedTopics = 2,
                totalTopics = 5,
                topicProgress = listOf(
                    TopicProgressUiModel(title = Res.string.topic_history_title, progress = 0.65f),
                    TopicProgressUiModel(
                        title = Res.string.topic_rights_title,
                        progress = 0.40f
                    ),
                    TopicProgressUiModel(
                        title = Res.string.topic_government_title,
                        progress = 0.80f
                    ),
                    TopicProgressUiModel(
                        title = Res.string.topic_elections_title,
                        progress = 0.25f
                    ),
                    TopicProgressUiModel(
                        title = Res.string.topic_geography_title,
                        progress = 0.50f
                    )
                )
            ),
            onBottomNavItemSelected = {}
        )
    }
}
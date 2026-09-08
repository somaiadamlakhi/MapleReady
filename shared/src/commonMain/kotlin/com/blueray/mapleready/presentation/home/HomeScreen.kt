package com.blueray.mapleready.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blueray.mapleready.presentation.theme.MapleReadyTheme
import org.jetbrains.compose.resources.stringResource

import mapleready.shared.generated.resources.*

@Composable
fun HomeScreen(
    state: HomeUiState = HomeUiState(),
    onStudyClick: () -> Unit = {},
    onPracticeClick: () -> Unit = {},
    onMockTestClick: () -> Unit = {}
) {
    HomeContent(
        state = state,
        onStudyClick = onStudyClick,
        onPracticeClick = onPracticeClick,
        onMockTestClick = onMockTestClick
    )
}

@Composable
private fun HomeContent(
    state: HomeUiState,
    onStudyClick: () -> Unit,
    onPracticeClick: () -> Unit,
    onMockTestClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Spacer(Modifier.height(24.dp))

        Text(
            text = "🍁",
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Text(
            text = stringResource(Res.string.home_title),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Text(
            text = stringResource(Res.string.home_subtitle),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(12.dp))

        HomeActionCard(
            title = stringResource(Res.string.study_title),
            description = stringResource(Res.string.study_description),
            onClick = onStudyClick
        )

        HomeActionCard(
            title = stringResource(Res.string.practice_title),
            description = stringResource(Res.string.practice_description),
            onClick = onPracticeClick
        )

        HomeActionCard(
            title = stringResource(Res.string.mock_test_title),
            description = stringResource(Res.string.mock_test_description),
            onClick = onMockTestClick
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = stringResource(Res.string.readiness_title),
            style = MaterialTheme.typography.titleMedium
        )

        LinearProgressIndicator(
            progress = { state.readiness },
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = stringResource(
                Res.string.readiness_percentage,
                (state.readiness * 100).toInt()
            ),
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.align(Alignment.End)
        )
    }
}

@Composable
private fun HomeActionCard(
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    MapleReadyTheme {
        HomeContent(
            state = HomeUiState(
                readiness = 0.72f
            ),
            onStudyClick = {},
            onPracticeClick = {},
            onMockTestClick = {}
        )
    }
}
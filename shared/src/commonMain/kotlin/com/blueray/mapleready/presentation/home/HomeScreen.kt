package com.blueray.mapleready.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.TrackChanges
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blueray.mapleready.presentation.components.MapleReadyActionCard
import com.blueray.mapleready.presentation.theme.MapleGold
import com.blueray.mapleready.presentation.theme.MapleReadyTheme
import com.blueray.mapleready.presentation.theme.MapleRed
import com.blueray.mapleready.presentation.theme.MockTestBackground
import com.blueray.mapleready.presentation.theme.PracticeBackground
import com.blueray.mapleready.presentation.theme.ProgressTrack
import com.blueray.mapleready.presentation.theme.StudyBackground
import mapleready.shared.generated.resources.Res
import mapleready.shared.generated.resources.app_name
import mapleready.shared.generated.resources.home_motivation
import mapleready.shared.generated.resources.home_subtitle
import mapleready.shared.generated.resources.mock_test_description
import mapleready.shared.generated.resources.mock_test_title
import mapleready.shared.generated.resources.practice_description
import mapleready.shared.generated.resources.practice_title
import mapleready.shared.generated.resources.readiness_percentage
import mapleready.shared.generated.resources.readiness_title
import mapleready.shared.generated.resources.study_description
import mapleready.shared.generated.resources.study_title
import org.jetbrains.compose.resources.stringResource

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
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        HomeHeader()

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        MapleReadyActionCard(
            title = stringResource(Res.string.study_title),
            description = stringResource(Res.string.study_description),
            icon = Icons.Outlined.MenuBook,
            containerColor = StudyBackground,
            iconColor = MapleRed,
            onClick = onStudyClick
        )

        MapleReadyActionCard(
            title = stringResource(Res.string.practice_title),
            description = stringResource(Res.string.practice_description),
            icon = Icons.Outlined.Description,
            containerColor = PracticeBackground,
            iconColor = MapleGold,
            onClick = onPracticeClick
        )

        MapleReadyActionCard(
            title = stringResource(Res.string.mock_test_title),
            description = stringResource(Res.string.mock_test_description),
            icon = Icons.Outlined.TrackChanges,
            containerColor = MockTestBackground,
            iconColor = MaterialTheme.colorScheme.primary,
            onClick = onMockTestClick
        )

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        ReadinessSection(
            readiness = state.readiness
        )

        MotivationCard()
    }
}

@Composable
private fun HomeHeader() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "🍁",
            style = MaterialTheme.typography.displaySmall
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = stringResource(Res.string.app_name),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = stringResource(Res.string.home_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(
                alpha = 0.7f
            )
        )
    }
}

@Composable
private fun ReadinessSection(
    readiness: Float
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        val percentage = (readiness.coerceIn(0f, 1f) * 100).toInt()

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(
                    Res.string.readiness_title
                ),
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = stringResource(
                    Res.string.readiness_percentage,
                    (readiness * 100).toInt(),
                    percentage
                ),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        LinearProgressIndicator(
            progress = { readiness },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            color = MaterialTheme.colorScheme.primary,
            trackColor = ProgressTrack
        )
    }
}

@Composable
private fun MotivationCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = PracticeBackground.copy(
                alpha = 0.55f
            )
        )
    ) {
        Text(
            text = stringResource(
                Res.string.home_motivation
            ),
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
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
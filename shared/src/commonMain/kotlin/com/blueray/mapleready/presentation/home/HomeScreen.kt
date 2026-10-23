package com.blueray.mapleready.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blueray.mapleready.domain.language.AppLanguage
import com.blueray.mapleready.presentation.components.BottomNavItem
import com.blueray.mapleready.presentation.components.MapleReadyActionCard
import com.blueray.mapleready.presentation.components.MapleReadyBottomBar
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
import mapleready.shared.generated.resources.language_dialog_cancel
import mapleready.shared.generated.resources.language_dialog_confirm
import mapleready.shared.generated.resources.language_dialog_msg
import mapleready.shared.generated.resources.language_dialog_title
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
    selectedLanguage: AppLanguage = AppLanguage.ENGLISH,
    onLanguageChange: (AppLanguage) -> Unit = {},
    onStudyClick: () -> Unit = {},
    onPracticeClick: () -> Unit = {},
    onMockTestClick: () -> Unit = {},
    onBottomNavItemSelected: (BottomNavItem) -> Unit = {}
) {
    var pendingLanguage by remember { mutableStateOf<AppLanguage?>(null) }

    Scaffold(
        bottomBar = {
            MapleReadyBottomBar(
                selectedItem = BottomNavItem.HOME,
                onItemSelected = onBottomNavItemSelected
            )
        }
    ) { innerPadding ->

        HomeContent(
            state = state,
            selectedLanguage = selectedLanguage,
            onLanguageChangeRequested = { targetLanguage ->
                if (targetLanguage != selectedLanguage) {
                    pendingLanguage = targetLanguage
                }
            },
            onStudyClick = onStudyClick,
            onPracticeClick = onPracticeClick,
            onMockTestClick = onMockTestClick,
            modifier = Modifier.padding(innerPadding)
        )

        pendingLanguage?.let { targetLang ->
            AlertDialog(
                onDismissRequest = { pendingLanguage = null },
                title = {
                    Text(text = stringResource(Res.string.language_dialog_title))
                },
                text = {
                    Text(text = stringResource(Res.string.language_dialog_msg))
                },
                confirmButton = {
                    Button(
                        onClick = {
                            val target = targetLang
                            pendingLanguage = null
                            onLanguageChange(target)
                        }
                    ) {
                        Text(text = stringResource(Res.string.language_dialog_confirm))
                    }
                },
                dismissButton = {
                    TextButton(onClick = { pendingLanguage = null }) {
                        Text(text = stringResource(Res.string.language_dialog_cancel))
                    }
                }
            )
        }
    }
}

@Composable
private fun HomeContent(
    state: HomeUiState,
    selectedLanguage: AppLanguage,
    onLanguageChangeRequested: (AppLanguage) -> Unit,
    onStudyClick: () -> Unit,
    onPracticeClick: () -> Unit,
    onMockTestClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        HomeHeader(
            selectedLanguage = selectedLanguage,
            onLanguageChangeRequested = onLanguageChangeRequested
        )

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
private fun HomeHeader(
    selectedLanguage: AppLanguage,
    onLanguageChangeRequested: (AppLanguage) -> Unit
) {
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

        LanguageSelectorToggle(
            selectedLanguage = selectedLanguage,
            onLanguageChangeRequested = onLanguageChangeRequested
        )
    }
}

@Composable
private fun LanguageSelectorToggle(
    selectedLanguage: AppLanguage,
    onLanguageChangeRequested: (AppLanguage) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier.padding(top = 10.dp)
    ) {
        Row(
            modifier = Modifier.padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LanguageChip(
                label = "🇨🇦 EN",
                isSelected = selectedLanguage == AppLanguage.ENGLISH,
                onClick = { onLanguageChangeRequested(AppLanguage.ENGLISH) }
            )

            LanguageChip(
                label = "🇫🇷 FR",
                isSelected = selectedLanguage == AppLanguage.FRENCH,
                onClick = { onLanguageChangeRequested(AppLanguage.FRENCH) }
            )
        }
    }
}

@Composable
private fun LanguageChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelLarge,
            color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
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
        HomeScreen(
            state = HomeUiState(
                readiness = 0.72f
            ),
            selectedLanguage = AppLanguage.ENGLISH,
            onLanguageChange = {},
            onStudyClick = {},
            onPracticeClick = {},
            onMockTestClick = {},
            onBottomNavItemSelected = {}
        )
    }
}

package com.blueray.mapleready.presentation.practice

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.blueray.mapleready.domain.practice.model.AnswerOption
import com.blueray.mapleready.domain.study.model.StudyTopicId
import mapleready.shared.generated.resources.Res
import mapleready.shared.generated.resources.practice_back
import mapleready.shared.generated.resources.practice_check_answer
import mapleready.shared.generated.resources.practice_correct
import mapleready.shared.generated.resources.practice_finish
import mapleready.shared.generated.resources.practice_incorrect
import mapleready.shared.generated.resources.practice_next_question
import mapleready.shared.generated.resources.practice_no_questions
import mapleready.shared.generated.resources.practice_question_progress
import mapleready.shared.generated.resources.practice_quit
import mapleready.shared.generated.resources.practice_result_excellent_desc
import mapleready.shared.generated.resources.practice_result_excellent_title
import mapleready.shared.generated.resources.practice_result_good_desc
import mapleready.shared.generated.resources.practice_result_good_title
import mapleready.shared.generated.resources.practice_result_needs_practice_desc
import mapleready.shared.generated.resources.practice_result_needs_practice_title
import mapleready.shared.generated.resources.practice_results_accuracy
import mapleready.shared.generated.resources.practice_results_score
import mapleready.shared.generated.resources.practice_results_title
import mapleready.shared.generated.resources.practice_title
import mapleready.shared.generated.resources.practice_try_again
import mapleready.shared.generated.resources.topic_elections_title
import mapleready.shared.generated.resources.topic_geography_title
import mapleready.shared.generated.resources.topic_government_title
import mapleready.shared.generated.resources.topic_history_title
import mapleready.shared.generated.resources.topic_rights_title
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun PracticeScreen(
    state: PracticeUiState,
    onAnswerClick: (Long) -> Unit,
    onCheckAnswerClick: () -> Unit,
    onNextQuestionClick: () -> Unit,
    onTryAgainClick: () -> Unit = {},
    onBackClick: () -> Unit,
    onFinishClick: () -> Unit,
) {

    if (state.isCompleted) {
        PracticeCompletedContent(
            state = state,
            onQuitClick = onFinishClick,
            onTryAgainClick = onTryAgainClick
        )
        return
    }

    val question = state.currentQuestion

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(
                horizontal = 20.dp,
                vertical = 12.dp
            )
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(
                        Res.string.practice_back
                    )
                )
            }

            Text(
                text = stringResource(
                    Res.string.practice_title
                ),
                style = MaterialTheme.typography.headlineSmall
            )
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        if (question == null) {

            Text(
                text = stringResource(
                    Res.string.practice_no_questions
                ),
                style = MaterialTheme.typography.bodyLarge
            )

            return@Column
        }

        Text(
            text = stringResource(
                question.topicId.toTitleResource()
            ),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Text(
            text = stringResource(
                Res.string.practice_question_progress,
                state.currentQuestionNumber,
                state.totalQuestions
            ),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        LinearProgressIndicator(
            progress = {
                if (state.totalQuestions == 0) {
                    0f
                } else {
                    state.currentQuestionNumber.toFloat() /
                            state.totalQuestions.toFloat()
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(28.dp)
        )

        Text(
            text = question.text,
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            question.answers.forEach { answer ->

                PracticeAnswerCard(
                    answer = answer,
                    isSelected =
                        answer.id == state.selectedAnswerId,
                    isAnswerChecked =
                        state.isAnswerChecked,
                    onClick = {
                        onAnswerClick(answer.id)
                    }
                )
            }
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        if (state.isAnswerChecked) {

            PracticeResultMessage(
                isCorrect = state.selectedAnswerIsCorrect
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    if (state.isLastQuestion) {
                        onFinishClick()
                    } else {
                        onNextQuestionClick()
                    }
                }
            ) {
                Text(
                    text = stringResource(
                        if (state.isLastQuestion) {
                            Res.string.practice_finish
                        } else {
                            Res.string.practice_next_question
                        }
                    )
                )
            }

        } else {

            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = state.selectedAnswerId != null,
                onClick = onCheckAnswerClick
            ) {
                Text(
                    text = stringResource(
                        Res.string.practice_check_answer
                    )
                )
            }
        }
    }
}

@Composable
private fun PracticeAnswerCard(
    answer: AnswerOption,
    isSelected: Boolean,
    isAnswerChecked: Boolean,
    onClick: () -> Unit
) {

    val isCorrectAnswer =
        isAnswerChecked && answer.isCorrect

    val isWrongSelectedAnswer =
        isAnswerChecked &&
                isSelected &&
                !answer.isCorrect

    val borderColor =
        when {
            isCorrectAnswer ->
                MaterialTheme.colorScheme.primary

            isWrongSelectedAnswer ->
                MaterialTheme.colorScheme.error

            isSelected ->
                MaterialTheme.colorScheme.primary

            else ->
                MaterialTheme.colorScheme.outlineVariant
        }

    val containerColor =
        when {
            isCorrectAnswer ->
                MaterialTheme.colorScheme.primaryContainer

            isWrongSelectedAnswer ->
                MaterialTheme.colorScheme.errorContainer

            isSelected ->
                MaterialTheme.colorScheme.secondaryContainer

            else ->
                MaterialTheme.colorScheme.surfaceVariant
        }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                enabled = !isAnswerChecked,
                onClick = onClick
            ),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = 1.dp,
            color = borderColor
        ),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Surface(
                shape = CircleShape,
                border = BorderStroke(
                    width = 1.dp,
                    color = borderColor
                ),
                color = containerColor
            ) {

                Box(
                    modifier = Modifier.padding(6.dp),
                    contentAlignment = Alignment.Center
                ) {

                    when {
                        isCorrectAnswer -> {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }

                        isWrongSelectedAnswer -> {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }

            Spacer(
                modifier = Modifier.padding(
                    horizontal = 8.dp
                )
            )

            Text(
                text = answer.text,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun PracticeResultMessage(
    isCorrect: Boolean
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Icon(
            imageVector =
                if (isCorrect) {
                    Icons.Default.Check
                } else {
                    Icons.Default.Close
                },
            contentDescription = null
        )

        Text(
            text =
                if (isCorrect) {
                    stringResource(
                        Res.string.practice_correct
                    )
                } else {
                    stringResource(
                        Res.string.practice_incorrect
                    )
                },
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
private fun PracticeCompletedContent(
    state: PracticeUiState,
    onQuitClick: () -> Unit,
    onTryAgainClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Text(
            text = stringResource(Res.string.practice_results_title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )

        val resultInfo = when (state.performanceGrade) {
            PracticePerformanceGrade.EXCELLENT -> PracticeResultUiModel(
                titleRes = Res.string.practice_result_excellent_title,
                descRes = Res.string.practice_result_excellent_desc,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                iconVector = Icons.Default.Check
            )
            PracticePerformanceGrade.GOOD -> PracticeResultUiModel(
                titleRes = Res.string.practice_result_good_title,
                descRes = Res.string.practice_result_good_desc,
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                iconVector = Icons.Default.Check
            )
            PracticePerformanceGrade.NEEDS_PRACTICE -> PracticeResultUiModel(
                titleRes = Res.string.practice_result_needs_practice_title,
                descRes = Res.string.practice_result_needs_practice_desc,
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                iconVector = Icons.Default.Close
            )
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = resultInfo.containerColor)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.size(64.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            imageVector = resultInfo.iconVector,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }

                Text(
                    text = stringResource(resultInfo.titleRes),
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = stringResource(resultInfo.descRes),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(4.dp))

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = stringResource(
                            Res.string.practice_results_score,
                            state.correctAnswersCount,
                            state.totalQuestions
                        ),
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        text = stringResource(
                            Res.string.practice_results_accuracy,
                            state.percentageScore
                        ),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                LinearProgressIndicator(
                    progress = { state.percentageScore / 100f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.surface
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onTryAgainClick
            ) {
                Text(text = stringResource(Res.string.practice_try_again))
            }

            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onQuitClick
            ) {
                Text(text = stringResource(Res.string.practice_quit))
            }
        }
    }
}

private data class PracticeResultUiModel(
    val titleRes: StringResource,
    val descRes: StringResource,
    val containerColor: Color,
    val iconVector: ImageVector
)

private fun StudyTopicId.toTitleResource(): StringResource {
    return when (this) {
        StudyTopicId.HISTORY -> Res.string.topic_history_title
        StudyTopicId.RIGHTS_AND_RESPONSIBILITIES -> Res.string.topic_rights_title
        StudyTopicId.GOVERNMENT -> Res.string.topic_government_title
        StudyTopicId.ELECTIONS -> Res.string.topic_elections_title
        StudyTopicId.GEOGRAPHY -> Res.string.topic_geography_title
    }
}
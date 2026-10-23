package com.blueray.mapleready.presentation.mocktest

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.blueray.mapleready.domain.practice.model.AnswerOption
import com.blueray.mapleready.domain.practice.model.PracticeQuestion
import com.blueray.mapleready.domain.study.model.StudyTopicId
import com.blueray.mapleready.presentation.theme.MapleGold
import com.blueray.mapleready.presentation.theme.MapleRed
import mapleready.shared.generated.resources.Res
import mapleready.shared.generated.resources.mock_test_exit
import mapleready.shared.generated.resources.mock_test_failed_desc
import mapleready.shared.generated.resources.mock_test_failed_title
import mapleready.shared.generated.resources.mock_test_flag
import mapleready.shared.generated.resources.mock_test_passed_desc
import mapleready.shared.generated.resources.mock_test_passed_title
import mapleready.shared.generated.resources.mock_test_retake
import mapleready.shared.generated.resources.mock_test_review_title
import mapleready.shared.generated.resources.mock_test_rule_1
import mapleready.shared.generated.resources.mock_test_rule_2
import mapleready.shared.generated.resources.mock_test_rule_3
import mapleready.shared.generated.resources.mock_test_start_button
import mapleready.shared.generated.resources.mock_test_start_subtitle
import mapleready.shared.generated.resources.mock_test_start_title
import mapleready.shared.generated.resources.mock_test_submit_button
import mapleready.shared.generated.resources.mock_test_submit_dialog_cancel
import mapleready.shared.generated.resources.mock_test_submit_dialog_confirm
import mapleready.shared.generated.resources.mock_test_submit_dialog_msg
import mapleready.shared.generated.resources.mock_test_submit_dialog_title
import mapleready.shared.generated.resources.practice_back
import mapleready.shared.generated.resources.practice_next_question
import mapleready.shared.generated.resources.previous
import mapleready.shared.generated.resources.topic_elections_title
import mapleready.shared.generated.resources.topic_geography_title
import mapleready.shared.generated.resources.topic_government_title
import mapleready.shared.generated.resources.topic_history_title
import mapleready.shared.generated.resources.topic_rights_title
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun MockTestScreen(
    state: MockTestUiState,
    onStartTestClick: () -> Unit,
    onAnswerClick: (Long) -> Unit,
    onToggleFlagClick: () -> Unit,
    onQuestionIndexClick: (Int) -> Unit,
    onNextQuestionClick: () -> Unit,
    onPreviousQuestionClick: () -> Unit,
    onRequestSubmitClick: () -> Unit,
    onDismissSubmitDialog: () -> Unit,
    onConfirmSubmitClick: () -> Unit,
    onRetakeClick: () -> Unit,
    onBackClick: () -> Unit
) {
    when (state.stage) {
        MockTestStage.NOT_STARTED -> {
            MockTestStartContent(
                onStartClick = onStartTestClick,
                onBackClick = onBackClick
            )
        }

        MockTestStage.IN_PROGRESS -> {
            MockTestExamContent(
                state = state,
                onAnswerClick = onAnswerClick,
                onToggleFlagClick = onToggleFlagClick,
                onQuestionIndexClick = onQuestionIndexClick,
                onNextQuestionClick = onNextQuestionClick,
                onPreviousQuestionClick = onPreviousQuestionClick,
                onRequestSubmitClick = onRequestSubmitClick,
                onBackClick = onBackClick
            )

            if (state.showSubmitConfirmation) {
                MockTestSubmitDialog(
                    answeredCount = state.answeredQuestionsCount,
                    totalCount = state.totalQuestions,
                    onConfirm = onConfirmSubmitClick,
                    onDismiss = onDismissSubmitDialog
                )
            }
        }

        MockTestStage.COMPLETED -> {
            MockTestResultsContent(
                state = state,
                onRetakeClick = onRetakeClick,
                onBackHomeClick = onBackClick
            )
        }
    }
}

@Composable
private fun MockTestStartContent(
    onStartClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(Res.string.practice_back)
                )
            }
            Text(
                text = stringResource(Res.string.mock_test_start_title),
                style = MaterialTheme.typography.titleLarge
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "🍁",
            style = MaterialTheme.typography.displayMedium
        )

        Text(
            text = stringResource(Res.string.mock_test_start_title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )

        Text(
            text = stringResource(Res.string.mock_test_start_subtitle),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                RuleItem(
                    text = stringResource(Res.string.mock_test_rule_1)
                )
                RuleItem(
                    text = stringResource(Res.string.mock_test_rule_2)
                )
                RuleItem(
                    text = stringResource(Res.string.mock_test_rule_3)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            modifier = Modifier.fillMaxWidth().height(52.dp),
            onClick = onStartClick
        ) {
            Text(
                text = stringResource(Res.string.mock_test_start_button),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
private fun RuleItem(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(8.dp)
        ) {}
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun MockTestExamContent(
    state: MockTestUiState,
    onAnswerClick: (Long) -> Unit,
    onToggleFlagClick: () -> Unit,
    onQuestionIndexClick: (Int) -> Unit,
    onNextQuestionClick: () -> Unit,
    onPreviousQuestionClick: () -> Unit,
    onRequestSubmitClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val question = state.currentQuestion

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(Res.string.practice_back)
                )
            }

            TimerChip(remainingTimeSeconds = state.remainingTimeSeconds)

            IconButton(onClick = onToggleFlagClick) {
                Icon(
                    imageVector = if (state.isCurrentQuestionFlagged) Icons.Default.Flag else Icons.Outlined.Flag,
                    contentDescription = stringResource(Res.string.mock_test_flag),
                    tint = if (state.isCurrentQuestionFlagged) MapleRed else MaterialTheme.colorScheme.onSurface
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Question Navigator Grid Row
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(state.questions) { index, q ->
                val isSelected = index == state.currentQuestionIndex
                val isAnswered = state.userAnswers.containsKey(q.id)
                val isFlagged = state.flaggedQuestionIds.contains(q.id)

                QuestionChip(
                    number = index + 1,
                    isSelected = isSelected,
                    isAnswered = isAnswered,
                    isFlagged = isFlagged,
                    onClick = { onQuestionIndexClick(index) }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (question == null) return@Column

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = stringResource(question.topicId.toTitleResource()),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Question ${state.currentQuestionNumber} of ${state.totalQuestions}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = question.text,
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                question.answers.forEach { answer ->
                    MockTestAnswerCard(
                        answer = answer,
                        isSelected = answer.id == state.selectedAnswerIdForCurrentQuestion,
                        onClick = { onAnswerClick(answer.id) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (state.currentQuestionIndex > 0) {
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = onPreviousQuestionClick
                ) {
                    Text(text = stringResource(Res.string.previous))
                }
            }

            if (state.currentQuestionIndex < state.totalQuestions - 1) {
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = onNextQuestionClick
                ) {
                    Text(text = stringResource(Res.string.practice_next_question))
                }
            } else {
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = onRequestSubmitClick,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(text = stringResource(Res.string.mock_test_submit_button))
                }
            }
        }
    }
}

@Composable
private fun TimerChip(remainingTimeSeconds: Long) {
    val minutes = remainingTimeSeconds / 60
    val seconds = remainingTimeSeconds % 60
    val formattedTime = "${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}"
    val isUrgent = remainingTimeSeconds < 300 // under 5 minutes

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = if (isUrgent) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.secondaryContainer
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Timer,
                contentDescription = null,
                tint = if (isUrgent) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = formattedTime,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = if (isUrgent) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun QuestionChip(
    number: Int,
    isSelected: Boolean,
    isAnswered: Boolean,
    isFlagged: Boolean,
    onClick: () -> Unit
) {
    val containerColor = when {
        isSelected -> MaterialTheme.colorScheme.primaryContainer
        isAnswered -> MaterialTheme.colorScheme.secondaryContainer
        else -> MaterialTheme.colorScheme.surfaceVariant
    }

    val borderColor = when {
        isSelected -> MaterialTheme.colorScheme.primary
        isFlagged -> MapleGold
        else -> Color.Transparent
    }

    Surface(
        modifier = Modifier
            .size(40.dp)
            .clickable(onClick = onClick),
        shape = CircleShape,
        color = containerColor,
        border = BorderStroke(width = 1.5.dp, color = borderColor)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = number.toString(),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun MockTestAnswerCard(
    answer: AnswerOption,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = 1.dp,
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
        ),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = isSelected,
                onClick = onClick
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = answer.text,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun MockTestSubmitDialog(
    answeredCount: Int,
    totalCount: Int,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = stringResource(Res.string.mock_test_submit_dialog_title))
        },
        text = {
            Text(
                text = stringResource(
                    Res.string.mock_test_submit_dialog_msg,
                    answeredCount,
                    totalCount
                )
            )
        },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text(text = stringResource(Res.string.mock_test_submit_dialog_confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(Res.string.mock_test_submit_dialog_cancel))
            }
        }
    )
}

@Composable
private fun MockTestResultsContent(
    state: MockTestUiState,
    onRetakeClick: () -> Unit,
    onBackHomeClick: () -> Unit
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
            text = stringResource(Res.string.mock_test_review_title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (state.isPassed) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.errorContainer
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = stringResource(
                        if (state.isPassed) Res.string.mock_test_passed_title else Res.string.mock_test_failed_title
                    ),
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    color = if (state.isPassed) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                )

                Text(
                    text = stringResource(
                        if (state.isPassed) Res.string.mock_test_passed_desc else Res.string.mock_test_failed_desc
                    ),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${state.correctAnswersCount} / ${state.totalQuestions}",
                            style = MaterialTheme.typography.displaySmall,
                            fontWeight = FontWeight.Bold,
                            color = if (state.isPassed) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                        )
                        Text(
                            text = "Score (${state.scorePercentage}%)",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        val minutes = state.timeSpentSeconds / 60
                        val seconds = state.timeSpentSeconds % 60
                        Text(
                            text = "${minutes}m ${seconds}s",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Time Spent",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }
        }

        Text(
            text = "Question Review",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.Start)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            state.questions.forEachIndexed { index, question ->
                val selectedId = state.userAnswers[question.id]
                val correctAnswer = question.answers.firstOrNull { it.isCorrect }
                val selectedAnswer = question.answers.firstOrNull { it.id == selectedId }
                val isCorrect = selectedId == correctAnswer?.id

                ReviewQuestionCard(
                    number = index + 1,
                    question = question,
                    selectedAnswer = selectedAnswer,
                    correctAnswer = correctAnswer,
                    isCorrect = isCorrect
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                modifier = Modifier.fillMaxWidth().height(50.dp),
                onClick = onRetakeClick
            ) {
                Text(text = stringResource(Res.string.mock_test_retake))
            }

            OutlinedButton(
                modifier = Modifier.fillMaxWidth().height(50.dp),
                onClick = onBackHomeClick
            ) {
                Text(text = stringResource(Res.string.mock_test_exit))
            }
        }
    }
}

@Composable
private fun ReviewQuestionCard(
    number: Int,
    question: PracticeQuestion,
    selectedAnswer: AnswerOption?,
    correctAnswer: AnswerOption?,
    isCorrect: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isCorrect) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.4f)
        )
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
                    text = "Q$number • ${stringResource(question.topicId.toTitleResource())}",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Icon(
                    imageVector = if (isCorrect) Icons.Default.Check else Icons.Default.Close,
                    contentDescription = null,
                    tint = if (isCorrect) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                )
            }

            Text(
                text = question.text,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Your Answer: ${selectedAnswer?.text ?: "Unanswered"}",
                style = MaterialTheme.typography.bodyMedium,
                color = if (isCorrect) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                fontWeight = FontWeight.Medium
            )

            if (!isCorrect && correctAnswer != null) {
                Text(
                    text = "Correct Answer: ${correctAnswer.text}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

private fun StudyTopicId.toTitleResource(): StringResource {
    return when (this) {
        StudyTopicId.HISTORY -> Res.string.topic_history_title
        StudyTopicId.RIGHTS_AND_RESPONSIBILITIES -> Res.string.topic_rights_title
        StudyTopicId.GOVERNMENT -> Res.string.topic_government_title
        StudyTopicId.ELECTIONS -> Res.string.topic_elections_title
        StudyTopicId.GEOGRAPHY -> Res.string.topic_geography_title
    }
}

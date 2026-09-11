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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.unit.dp
import com.blueray.mapleready.domain.practice.model.AnswerOption
import mapleready.shared.generated.resources.Res
import mapleready.shared.generated.resources.practice_back
import mapleready.shared.generated.resources.practice_check_answer
import mapleready.shared.generated.resources.practice_correct
import mapleready.shared.generated.resources.practice_history
import mapleready.shared.generated.resources.practice_incorrect
import mapleready.shared.generated.resources.practice_next_question
import mapleready.shared.generated.resources.practice_no_questions
import mapleready.shared.generated.resources.practice_question_progress
import mapleready.shared.generated.resources.practice_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun PracticeScreen(
    state: PracticeUiState,
    onAnswerClick: (Long) -> Unit,
    onCheckAnswerClick: () -> Unit,
    onNextQuestionClick: () -> Unit,
    onBackClick: () -> Unit
) {

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
                Res.string.practice_history
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
                onClick = onNextQuestionClick
            ) {
                Text(
                    text = stringResource(
                        Res.string.practice_next_question
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

    val borderColor =
        when {
            isAnswerChecked && answer.isCorrect ->
                MaterialTheme.colorScheme.primary

            isSelected ->
                MaterialTheme.colorScheme.primary

            else ->
                MaterialTheme.colorScheme.outlineVariant
        }

    val containerColor =
        when {
            isAnswerChecked && answer.isCorrect ->
                MaterialTheme.colorScheme.primaryContainer

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
                modifier = Modifier,
                shape = CircleShape,
                border = BorderStroke(
                    width = 1.dp,
                    color = borderColor
                )
            ) {

                Box(
                    modifier = Modifier.padding(6.dp),
                    contentAlignment = Alignment.Center
                ) {

                    if (isAnswerChecked && answer.isCorrect) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Text(
                text = answer.text,
                style = MaterialTheme.typography.bodyLarge
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
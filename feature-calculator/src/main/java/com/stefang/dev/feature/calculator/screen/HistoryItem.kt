package com.stefang.dev.feature.calculator.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stefang.dev.core.ui.ScanMeTheme
import com.stefang.dev.feature.calculator.model.ArithmeticData

@Composable
fun HistoryItem(data: ArithmeticData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            ProvideTextStyle(MaterialTheme.typography.titleMedium) {
                Text(text = "Input: ${data.getInput()}")
                Spacer(modifier = Modifier.size(4.dp))
                Text(text = "Result: ${data.getResult()}")
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ScanMeTheme {
        HistoryItem(
            ArithmeticData(
                firstOperand = 10.0,
                secondOperand = 10.0,
                operator = '+',
                result = 10.0,
            )
        )
    }
}

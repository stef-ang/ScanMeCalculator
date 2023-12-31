package com.stefang.dev.feature.calculator.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stefang.dev.core.ui.ScanMeTheme
import com.stefang.dev.feature.calculator.model.ArithmeticData
import com.stefang.dev.feature.calculator.model.UsedStorage
import com.stefang.dev.feature.calculator.viewmodel.MainViewModel

@Composable
fun MainScreenRoute(
    viewModel: MainViewModel = hiltViewModel()
) {
    val histories by viewModel.allHistories.collectAsStateWithLifecycle()
    val selectedStorage by viewModel.storageType.collectAsStateWithLifecycle()

    MainScreen(histories, selectedStorage, viewModel::setStorage)
}

@Composable
fun MainScreen(
    histories: List<ArithmeticData>,
    usedStorage: UsedStorage,
    onUpdateStorage: (UsedStorage) -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        StorageOptionsContainer(usedStorage, onUpdateStorage)

        Spacer(modifier = Modifier.size(16.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(histories) { item ->
                HistoryItem(data = item)
            }
        }
    }
}

@Composable
private fun StorageOptionsContainer(
    usedStorage: UsedStorage,
    onUpdateStorage: (UsedStorage) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                1.dp,
                MaterialTheme.colorScheme.tertiary,
                RoundedCornerShape(8.dp),
            )
            .padding(8.dp)
    ) {
        Text(
            text = "Use storage:",
            fontSize = 12.sp
        )

//        var storageType by remember { mutableStateOf(usedStorage) }
        Row(
            Modifier
                .fillMaxWidth()
                .selectableGroup()
        ) {
            Row(
                Modifier
                    .weight(1f)
                    .selectable(
                        selected = usedStorage == UsedStorage.Database,
                        onClick = { onUpdateStorage(UsedStorage.Database) },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = usedStorage == UsedStorage.Database,
                    onClick = { onUpdateStorage(UsedStorage.Database) }
                )
                Text(text = "Database")
            }
            Row(
                Modifier
                    .weight(1f)
                    .selectable(
                        selected = usedStorage == UsedStorage.File,
                        onClick = { onUpdateStorage(UsedStorage.File) },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = usedStorage == UsedStorage.File,
                    onClick = { onUpdateStorage(UsedStorage.File) }
                )
                Text(text = "File")
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ScanMeTheme {
        Scaffold {
            MainScreen(
                histories = listOf(
                    ArithmeticData(
                        firstOperand = 10.0,
                        secondOperand = 5.0,
                        operator = '+',
                        result = 15.0,
                    ),
                    ArithmeticData(
                        firstOperand = 10.0,
                        secondOperand = 5.0,
                        operator = '-',
                        result = 5.0,
                    ),
                    ArithmeticData(
                        firstOperand = 10.0,
                        secondOperand = 5.0,
                        operator = '*',
                        result = 50.0,
                    ),
                    ArithmeticData(
                        firstOperand = 10.0,
                        secondOperand = 5.0,
                        operator = '/',
                        result = 2.0,
                    )
                ),
                usedStorage = UsedStorage.File,
                onUpdateStorage = {}
            )
        }
    }
}

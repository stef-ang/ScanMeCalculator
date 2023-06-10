package com.stefang.dev.feature.calculator.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MainScreenRoute() {
    MainScreen()
}

@Composable
fun MainScreen() {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Text(text = "Main Screen")
    }
}

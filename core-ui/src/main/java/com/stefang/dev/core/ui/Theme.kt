/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.stefang.dev.core.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

enum class Theme { red, green }

@Composable
fun ScanMeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    theme: Theme = Theme.red,
    content: @Composable () -> Unit
) {
    val themePair = if (theme == Theme.red) {
        LightColorsRed to DarkColorsRed
    } else {
        LightColorsGreen to DarkColorsGreen
    }
    val colorScheme = if (darkTheme) themePair.second else themePair.first

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

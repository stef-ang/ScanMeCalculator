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
package com.stefang.dev.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.stefang.dev.BuildConfig
import com.stefang.dev.core.ui.Theme
import com.stefang.image.source.api.ImageSourceApi
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var imageSourceApi: ImageSourceApi

    private val viewModel: MainAppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageSourceApi.initLauncher(this) {
            viewModel.setUriImage(it)
        }
        val theme = if (BuildConfig.THEME == "RED") Theme.Red else Theme.Green
        setContent {
            MainAppScreen(
                theme = theme,
                viewModel = viewModel,
                onAccessImageSource = ::accessImageSource
            )
        }
    }

    private fun accessImageSource() {
        imageSourceApi.runLauncher()
    }
}

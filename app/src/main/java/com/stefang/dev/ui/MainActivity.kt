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
@file:OptIn(ExperimentalMaterial3Api::class)

package com.stefang.dev.ui

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.stefang.dev.R
import com.stefang.dev.core.ui.MyApplicationTheme
import com.stefang.dev.feature.calculator.parcelable
import com.stefang.image.source.api.ImageSourceApi
import com.stefang.image.source.api.ImageSourceApi.Companion.EXTRA_PHOTO_URI
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var imageSourceApi: ImageSourceApi

    private val viewModel: MainViewModel by viewModels()

    private val imageSourceLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val uri = it.data?.parcelable<Uri>(EXTRA_PHOTO_URI)
            viewModel.setUriImage(uri ?: Uri.EMPTY)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainAppScreen(
                viewModel = viewModel,
                onAccessImageSource = ::accessImageSource
            )
        }
    }

    private fun accessImageSource() {
        imageSourceLauncher.launch(imageSourceApi.getAccessImageIntent(this))
    }
}

@Composable
private fun MainAppScreen(
    viewModel: MainViewModel,
    onAccessImageSource: () -> Unit
) {
    MyApplicationTheme {
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        val currentScreen = allScreens.find {
            it.route == currentDestination?.route?.substringBefore('/')
        } ?: MainScreen

        LaunchedEffect(Unit) {
            viewModel.uriImageFlow.collect {
                val uriString = it.encode()
                navController.navigateSingleTopTo(
                    "${PreviewScreen.route}/$uriString"
                )
            }
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(id = currentScreen.titleRes)) },
                    navigationIcon = {
                        if (currentScreen != MainScreen) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .clickable {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                )
            },
            floatingActionButton = {
                if (currentScreen == MainScreen) {
                    FloatingActionButton(onClick = { onAccessImageSource() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_photo_camera_24),
                            contentDescription = null
                        )
                    }
                }
            }
        ) { innerPadding ->
            AppNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}

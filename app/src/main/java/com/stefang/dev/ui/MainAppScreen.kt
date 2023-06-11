@file:OptIn(ExperimentalMaterial3Api::class)
package com.stefang.dev.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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

@Composable
fun MainAppScreen(
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

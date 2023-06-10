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

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.stefang.dev.R
import com.stefang.dev.feature.calculator.screen.ImagePreviewScreen
import com.stefang.dev.feature.calculator.screen.MainScreenRoute
import java.net.URLDecoder
import java.net.URLEncoder

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {

    NavHost(
        navController = navController,
        startDestination = MainScreen.route,
        modifier = modifier
    ) {
        composable(MainScreen.route) {
            MainScreenRoute()
        }

        composable(
            route = PreviewScreen.routeWithArgs,
            arguments = PreviewScreen.arguments
        ) {
            val uri = it.arguments?.getString(PreviewScreen.uriArg)?.decode()
            ImagePreviewScreen(photoUri = uri)
        }
    }
}

fun Uri.encode(): String {
    return URLEncoder.encode(this.toString(), "UTF-8")
}

fun String.decode(): Uri {
    val decodedString = URLDecoder.decode(this, "UTF-8")
    return Uri.parse(decodedString)
}

fun NavHostController.navigateSingleTopTo(route: String) {
    this.navigate(route) {
        launchSingleTop = true
    }
}

interface AppDestination {
    val route: String
    val titleRes: Int
}

object MainScreen : AppDestination {
    override val route: String = "main"
    override val titleRes: Int = R.string.app_name
}

object PreviewScreen : AppDestination {
    override val route: String = "preview"
    override val titleRes: Int = R.string.preview_screen_title

    const val uriArg = "uri"
    val routeWithArgs = "$route/{$uriArg}"
    val arguments = listOf(
        navArgument(uriArg) { type = NavType.StringType }
    )
}

val allScreens = listOf(MainScreen, PreviewScreen)

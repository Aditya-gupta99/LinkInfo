package com.sparklead.linkinfo.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sparklead.linkinfo.ui.home.DashboardScreen

@Composable
fun Navigation(navController: NavHostController, padding: PaddingValues) {

    NavHost(navController = navController, startDestination = Screens.DashboardScreen.route) {
        composable(Screens.DashboardScreen.route) {
            DashboardScreen(padding)
        }
        composable(Screens.CoursesScreen.route) {

        }
        composable(Screens.AddLinkScreen.route) {

        }
        composable(Screens.CampaignsScreen.route) {

        }
        composable(Screens.ProfileScreen.route) {

        }
    }
}
package com.sparklead.linkinfo.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FabPosition
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sparklead.linkinfo.navigation.Navigation
import com.sparklead.linkinfo.navigation.Screens
import com.sparklead.linkinfo.ui.theme.NavigationBar
import com.sparklead.linkinfo.ui.theme.PrimaryBlue

@Composable
fun LinkInfo() {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val route = navBackStackEntry?.destination?.route

    androidx.compose.material.Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Column {
                route?.let {
                    NavigationBar(route = it) { target ->
                        navController.apply {
                            navigate(target) {
                                restoreState = true
                                launchSingleTop = true
                                graph.startDestinationRoute?.let {
                                    popUpTo(route = Screens.DashboardScreen.route) {
                                        saveState = true
                                    }
                                }
                            }
                        }
                    }
                }
            }

        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier.size(70.dp),
                    shape = CircleShape,
                    elevation = CardDefaults.cardElevation(0.dp),
                    colors = CardDefaults.cardColors(Color.White)
                ) {
                }
                FloatingActionButton(
                    containerColor = PrimaryBlue,
                    shape = CircleShape,
                    onClick = {
                        Screens.AddLinkScreen.route.let {
                            navController.navigate(it) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                        Screens.AddLinkScreen.route.let { navController.navigate(it) }
                    },
                    contentColor = Color.White
                ) {
                    Icon(
                        painter = painterResource(id = Screens.AddLinkScreen.icon),
                        contentDescription = "Add icon"
                    )
                }
            }

        }
    ) {
        Navigation(navController = navController, padding = it)
    }
}
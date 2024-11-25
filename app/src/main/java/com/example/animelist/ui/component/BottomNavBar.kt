package com.example.animelist.ui.component

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.example.animelist.navigation.TopLevelNavDestination
import com.example.animelist.ui.AppState
import kotlin.reflect.KClass


@Composable
fun BottomNavBar(
    appState: AppState,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier){
        TopLevelNavDestination.entries.forEach { topLeveDestination ->
            NavigationBarItem(
                selected = appState.currentDestination.isRouteInHierarchy(topLeveDestination.baseRoute),
                icon = { Icon(painter = painterResource(topLeveDestination.icon), contentDescription = null)},
                onClick = {appState.navigateToTopLevelNavDestination(topLeveDestination)}
            )
        }
    }
}

fun NavDestination?.isRouteInHierarchy(route: KClass<*>): Boolean = this?.hierarchy?.any{ it.hasRoute(route) } ?: false
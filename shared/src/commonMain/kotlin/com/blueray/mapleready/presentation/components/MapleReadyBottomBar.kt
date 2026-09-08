package com.blueray.mapleready.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.ShowChart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.blueray.mapleready.presentation.theme.SurfaceLight
import mapleready.shared.generated.resources.Res
import mapleready.shared.generated.resources.nav_home
import mapleready.shared.generated.resources.nav_progress
import mapleready.shared.generated.resources.nav_study
import org.jetbrains.compose.resources.stringResource

enum class BottomNavItem {
    HOME,
    STUDY,
    PROGRESS
}

@Composable
fun MapleReadyBottomBar(
    selectedItem: BottomNavItem,
    onItemSelected: (BottomNavItem) -> Unit
) {
    NavigationBar(
        containerColor = SurfaceLight
    ) {

        NavigationBarItem(
            selected = selectedItem == BottomNavItem.HOME,
            onClick = { onItemSelected(BottomNavItem.HOME) },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Home,
                    contentDescription = null
                )
            },
            label = {
                Text(
                    text = stringResource(Res.string.nav_home)
                )
            },
            colors = navigationItemColors()
        )

        NavigationBarItem(
            selected = selectedItem == BottomNavItem.STUDY,
            onClick = { onItemSelected(BottomNavItem.STUDY) },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.MenuBook,
                    contentDescription = null
                )
            },
            label = {
                Text(
                    text = stringResource(Res.string.nav_study)
                )
            },
            colors = navigationItemColors()
        )

        NavigationBarItem(
            selected = selectedItem == BottomNavItem.PROGRESS,
            onClick = { onItemSelected(BottomNavItem.PROGRESS) },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.ShowChart,
                    contentDescription = null
                )
            },
            label = {
                Text(
                    text = stringResource(Res.string.nav_progress)
                )
            },
            colors = navigationItemColors()
        )
    }
}

@Composable
private fun navigationItemColors() =
    NavigationBarItemDefaults.colors(
        selectedIconColor = MaterialTheme.colorScheme.primary,
        selectedTextColor = MaterialTheme.colorScheme.primary,
        indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.10f),
        unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.55f),
        unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.55f)
    )
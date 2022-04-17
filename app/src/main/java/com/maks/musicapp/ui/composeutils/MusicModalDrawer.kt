package com.maks.musicapp.ui.composeutils

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Feed
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.maks.musicapp.R
import com.maks.musicapp.ui.viewmodels.MusicViewModel
import com.maks.musicapp.utils.ModalDrawerConstants
import com.maks.musicapp.utils.Routes
import com.maks.musicapp.utils.setBackground
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MusicModalDrawer(
    drawerState: DrawerState,
    musicViewModel: MusicViewModel,
    navController: NavController,
    content: @Composable () -> Unit
) {
    val selectedItem = musicViewModel.musicViewModelStates.selectedModalDrawerItem

    val coroutineScope = rememberCoroutineScope()
    ModalDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Image(
                    painter = painterResource(id = R.drawable.music_logo),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.padding(8.dp))
                ListItem(
                    icon = { Icon(Icons.Filled.Home, "") },
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            musicViewModel.setSelectedModalDrawerItem(ModalDrawerConstants.MAIN_INDEX)
                            navController.navigate(Routes.MainScreenRoute.route)
                            coroutineScope.launch {
                                drawerState.close()
                            }
                        }
                        .setBackground(
                            selectedItem = selectedItem,
                            expectedItem = ModalDrawerConstants.MAIN_INDEX
                        ),
                    text = {
                        Text(text = "Main")
                    })
                ListItem(
                    icon = { Icon(Icons.Filled.Feed, "") },
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            musicViewModel.setSelectedModalDrawerItem(ModalDrawerConstants.FEEDS_INDEX)
                            navController.navigate(Routes.FeedsScreenRoute.route)
                            coroutineScope.launch {
                                drawerState.close()
                            }
                        }
                        .setBackground(
                            selectedItem = selectedItem,
                            expectedItem = ModalDrawerConstants.FEEDS_INDEX
                        ),
                    text = {
                        Text(text = "Feeds")
                    })
            }
        },
        content = {
            content()
        }
    )
}
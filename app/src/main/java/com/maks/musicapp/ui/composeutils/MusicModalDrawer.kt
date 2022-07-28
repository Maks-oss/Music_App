package com.maks.musicapp.ui.composeutils

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Feed
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.maks.musicapp.R
import com.maks.musicapp.ui.viewmodels.FeedsViewModel
import com.maks.musicapp.ui.viewmodels.MusicViewModel
import com.maks.musicapp.utils.AppConstants
import com.maks.musicapp.utils.ModalDrawerConstants
import com.maks.musicapp.utils.Routes
import com.maks.musicapp.utils.setBackground
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MusicModalDrawer(
    drawerState: DrawerState,
    musicViewModel: MusicViewModel,
    feedsViewModel: FeedsViewModel,
    navController: NavController,
    user: FirebaseUser?,
    userSignOut:()->Unit,
    content: @Composable () -> Unit
) {
    val selectedItem = musicViewModel.musicViewModelStates.selectedModalDrawerItem
    val coroutineScope = rememberCoroutineScope()
    user?.let { firebaseUser ->
        ModalDrawer(
            drawerState = drawerState,
            drawerContent = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
//                verticalArrangement = Arrangement.SpaceBetween
                ) {
                    DisplayUser(firebaseUser)

                    Spacer(modifier = Modifier.padding(8.dp))
                    SetupListItems(
                        coroutineScope,
                        musicViewModel,
                        drawerState,
                        navController,
                        selectedItem,
                        feedsViewModel
                    )
                    Divider()
                    ListItem(
                        icon = { Icon(Icons.Filled.Logout, "") },
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                coroutineScope.launch {
                                    Firebase.auth.signOut()
                                    userSignOut()
                                    drawerState.close()
                                }
                            }.background(MaterialTheme.colors.surface),
                        text = {
                            Text(text = "SignOut")
                        })

                }
            },
            content = {
                content()
            }
        )
    }
}

@Composable
private fun DisplayUser(user: FirebaseUser?) {
    Box(contentAlignment = Alignment.BottomStart) {
        Image(
            painter = painterResource(id = R.drawable.music_theme),
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(user?.photoUrl ?: AppConstants.DEFAULT_IMAGE)
                    .build(), contentDescription = "User Image",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.White, CircleShape)

            )
            Text(
                text = user?.email?:"",
                color = Color.White,
                modifier = Modifier

            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun SetupListItems(
    coroutineScope: CoroutineScope,
    musicViewModel: MusicViewModel,
    drawerState: DrawerState,
    navController: NavController,
    selectedItem: Int,
    feedsViewModel: FeedsViewModel
) {
    ListItem(
        icon = { Icon(Icons.Filled.Home, "") },
        modifier = Modifier

            .padding(8.dp)
            .clickable {
                coroutineScope.launch {
                    musicViewModel.setSelectedModalDrawerItem(ModalDrawerConstants.MAIN_INDEX)
                    drawerState.close()
                    navController.navigate(Routes.MainScreenRoute.route)
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
                coroutineScope.launch {
                    musicViewModel.setSelectedModalDrawerItem(ModalDrawerConstants.FEEDS_INDEX)
                    feedsViewModel.applyFeeds()
                    drawerState.close()
                    navController.navigate(Routes.FeedsScreenRoute.route)
                }
            }
            .setBackground(
                selectedItem = selectedItem,
                expectedItem = ModalDrawerConstants.FEEDS_INDEX
            ),
        text = {
            Text(text = "Feeds")
        })
    ListItem(
        icon = { Icon(Icons.Filled.Favorite, "") },
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                coroutineScope.launch {
                    musicViewModel.setSelectedModalDrawerItem(ModalDrawerConstants.FAVOURITES_INDEX)
//                    feedsViewModel.applyFeeds()
                    drawerState.close()
//                    navController.navigate(Routes.FeedsScreenRoute.route)
                }
            }
            .setBackground(
                selectedItem = selectedItem,
                expectedItem = ModalDrawerConstants.FAVOURITES_INDEX
            ),
        text = {
            Text(text = "Favourite Tracks")
        })
}
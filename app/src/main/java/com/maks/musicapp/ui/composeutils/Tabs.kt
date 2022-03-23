package com.maks.musicapp.ui.composeutils

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import com.maks.musicapp.utils.Routes
import com.maks.musicapp.utils.TabRoutes

@ExperimentalMaterialApi
@Composable
fun MusicTabs(tabIndex:Int,tabsList:List<TabRoutes>,tabAction:(Int)->Unit){
    TabRow(selectedTabIndex = tabIndex) {
        tabsList.forEachIndexed { index, tabRoutes ->
            LeadingIconTab(
                selected = index == tabIndex,
                onClick = {tabAction(index)},
                text = { Text(text = stringResource(id = tabRoutes.tabName)) },
                icon = { Icon(imageVector = tabRoutes.icon, contentDescription = "") }
            )
        }
    }
}
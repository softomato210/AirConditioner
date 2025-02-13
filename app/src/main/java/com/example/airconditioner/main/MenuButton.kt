package com.example.airconditioner.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.airconditioner.Screens
import com.websarva.wings.android.rental.R

@Composable
fun MenuButton(navController: NavController,
               modifier: Modifier = Modifier
) {

    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier=modifier
    ){
        IconButton(
            onClick={
                expanded = true
            },
            modifier= Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondaryContainer)
        ){
            Icon(
                imageVector= Icons.Default.Menu,
                contentDescription = null
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            offset = DpOffset(x=(-0).dp,y=(-8).dp)
        ) {
            DropdownMenuItem(
                text={ Text(stringResource(R.string.menu_input)) },
                onClick={
                    expanded = false
                    navController.navigate(Screens.HOME.name)
                }
            )
            DropdownMenuItem(
                text={ Text(stringResource(R.string.menu_registration)) },
                onClick={
                    expanded = false
                    navController.navigate(Screens.REGISTRATION.name)
                }
            )
        }
    }
}
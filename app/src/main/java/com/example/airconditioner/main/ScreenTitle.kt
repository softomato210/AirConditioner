package com.example.airconditioner.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ScreenTitle(
    value: String,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
    ){
        Text(
            text = value,
            fontSize = 36.sp,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(Alignment.Center)
        )
    }
}
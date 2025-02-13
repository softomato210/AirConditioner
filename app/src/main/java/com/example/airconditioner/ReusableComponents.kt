package com.example.airconditioner

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.airconditioner.database.AirConditioner
import com.websarva.wings.android.rental.R
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputRow(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType,
    leadingIcon: ImageVector
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            modifier = Modifier.weight(1f),
            fontSize = 24.sp
        )

        val interactionSource = remember { MutableInteractionSource() }

        TextField(
            value = if (label == "日付") selectedDate else value,
            onValueChange = {
                if (label != "日付") {
                    onValueChange(it)
                } else {
                    showDatePicker = false
                }
            },
            modifier = Modifier
                .height(62.dp)
                .width(220.dp)
                .onFocusChanged { focusState ->
                    if (label == "日付" && focusState.isFocused) {
                        showDatePicker = true
                    }
                },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = keyboardType
            ) ,
            textStyle = TextStyle(
                textAlign = TextAlign.End,
                fontSize = 24.sp
            ),
            placeholder = {
                Text(
                    text = "",
                    style = TextStyle(
                        color = Color.Gray,
                        fontSize = 20.sp
                    )
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = Color.Gray
                )
            },
            shape = RoundedCornerShape(8.dp),
            readOnly = label == "日付" ,
            interactionSource = interactionSource
        )

        if (showDatePicker) {

            val datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)

            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            datePickerState.selectedDateMillis?.let {

                                val selected = Instant.ofEpochMilli(it)
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate()

                                val formattedDate =
                                    selected.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
                                selectedDate = formattedDate
                                onValueChange(formattedDate)
                            }
                            showDatePicker = false
                        }
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showDatePicker = false }
                    ) {
                        Text("キャンセル")
                    }
                }
            ) {
                DatePicker(
                    state = datePickerState
                )
            }
        }
    }
}

@Composable
fun InputRowPainter(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType,
    leadingIcon: Painter
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            modifier = Modifier.weight(1f),
            fontSize = 24.sp
        )

        val interactionSource = remember { MutableInteractionSource() }

        TextField(
            value = value,
            onValueChange = { onValueChange(it) },
            modifier = Modifier
                .height(62.dp)
                .width(220.dp)
                .onFocusChanged {},
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = keyboardType
            ) ,
            textStyle = TextStyle(
                textAlign = TextAlign.End,
                fontSize = 24.sp
            ),
            placeholder = {
                Text(
                    text = "",
                    style = TextStyle(
                        color = Color.Gray,
                        fontSize = 20.sp
                    )
                )
            },
            leadingIcon = {
                Icon(
                    painter = leadingIcon,
                    contentDescription = null,
                    tint = Color.Gray
                )
            },
            shape = RoundedCornerShape(8.dp),
            interactionSource = interactionSource
        )
    }
}

@Composable
fun CheckboxRow(label: String,
                isChecked: Boolean,
                onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            modifier = Modifier.weight(1f),
            fontSize = 24.sp
        )
        Checkbox(
            checked = isChecked,
            modifier = Modifier
                .padding(end = 80.dp),
            onCheckedChange =  onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.onTertiaryContainer,
                uncheckedColor = MaterialTheme.colorScheme.onSecondaryContainer,
                checkmarkColor = MaterialTheme.colorScheme.onPrimary
            )
        )
    }
}

@Composable
fun AirConditionerItem(
    airConditioner: AirConditioner,
    onLongPress: () -> Unit,
    onDoubleTap: () -> Unit
) {
    val backgroundColor = when(airConditioner.outputFlg) {
        0 -> Color(0xFF90BE6D)
        1 -> Color.Gray
        2 -> Color.Gray
        else -> Color.Transparent
    }

    val textColor = when(airConditioner.outputFlg) {
        0 -> Color.Black
        1 -> Color.Black
        2 -> Color.Black
        else -> Color.Black
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .background(color = backgroundColor, shape = RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .padding(8.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {},
                    onDoubleTap = {}
                )
            }
    ) {
        Text(text = "登録日: ${airConditioner.date}", color = textColor)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "担当者: ${airConditioner.manager}", color = textColor)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "温度: ${airConditioner.temperature} ℃", color = textColor)
    }
}
package com.example.airconditioner

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.airconditioner.database.AirConditioner
import com.example.airconditioner.main.*
import com.example.airconditioner.ui.theme.AirConditionerTheme
import com.websarva.wings.android.rental.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AirConditionerTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .safeDrawingPadding(),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    val airConditionerViewModel: AirConditionerViewModel = viewModel()
                    Root(airConditionerViewModel = airConditionerViewModel)
                }
            }
        }
    }
}

@Composable
fun Root(
    airConditionerViewModel: AirConditionerViewModel) {

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.HOME.name,
        enterTransition = { fadeIn(animationSpec = tween(300)) },
        exitTransition = { fadeOut(animationSpec = tween(300)) }
    ) {
        composable(route = Screens.HOME.name) {
            HomeScreen(
                navController,
                airConditionerViewModel = airConditionerViewModel
            )
        }
        composable(route = Screens.REGISTRATION.name) {
            RegistrationScreen(
                navController,
                airConditionerViewModel = airConditionerViewModel
            )
        }
    }
}

@Composable
fun HomeScreen(
    navController: NavController,
    airConditionerViewModel: AirConditionerViewModel
) {
    val context = LocalContext.current


    LaunchedEffect(Unit) {
        try {

            airConditionerViewModel.getAllAirConditioner()

            //throw Exception("強制的に例外を発生させました")
        } catch (_: Exception) {
            Toast.makeText(context, "でけへん", Toast.LENGTH_LONG).show()
        }
    }

    var managerCdInput by rememberSaveable { mutableStateOf("") }
    var managerInput by rememberSaveable { mutableStateOf("") }
    var dateInput by rememberSaveable{ mutableStateOf("") }
    var temperatureInput by rememberSaveable{ mutableStateOf("") }
    var isAirConditionerClean by rememberSaveable { mutableStateOf(false) }
    var isFanClean by rememberSaveable { mutableStateOf(false) }
    var isIllumination by rememberSaveable { mutableStateOf(false) }
    val isLoading by airConditionerViewModel.isLoading.collectAsState()

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        val (btnSave,btnMenu,boxLoading) = createRefs()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            ScreenTitle(
                value = stringResource(R.string.title),
                modifier = Modifier
            )

            InputRow(
                label = stringResource(R.string.manager_code),
                value = managerCdInput,
                onValueChange = {
                    managerCdInput = it
                    airConditionerViewModel.searchPersonalByCode(it)
                                },
                keyboardType = KeyboardType.Number,
                leadingIcon = Icons.Default.Person
            )

            DropdownFromJsonFile(
                label = stringResource(R.string.manager),
                selectedItem = managerInput,
                onItemSelected = { selectedItem ->
                    managerInput = selectedItem
                    managerCdInput = airConditionerViewModel.getCdByName(selectedItem)
                },
                viewModel = airConditionerViewModel
            )

            InputRow(
                label = "日付",
                value = dateInput,
                onValueChange = { dateInput = it },
                keyboardType = KeyboardType.Number,
                leadingIcon = Icons.Default.DateRange
            )

            InputRowPainter(
                label = stringResource(R.string.temperature),
                value = temperatureInput,
                onValueChange = { temperatureInput = it },
                keyboardType = KeyboardType.Number,
                leadingIcon = painterResource(id = R.drawable.ic_tempurature)
            )

            CheckboxRow(
                label = stringResource(R.string.aircon_clean),
                isChecked = isAirConditionerClean,
                onCheckedChange = { isChecked ->
                    isAirConditionerClean = isChecked
                }
            )

            CheckboxRow(
                label = stringResource(R.string.fan_clean),
                isChecked = isFanClean,
                onCheckedChange = { isChecked ->
                    isFanClean = isChecked
                }
            )

            CheckboxRow(
                label = stringResource(R.string.illumination_clean),
                isChecked = isIllumination,
                onCheckedChange = { isChecked ->
                    isIllumination = isChecked
                }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(boxLoading) {
                    top.linkTo(parent.bottom)
                }
                .padding(4.dp)
                .padding(bottom = 200.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    strokeWidth = 6.dp,
                    modifier = Modifier
                        .size(60.dp)
                        .align(Alignment.Center)

                )
            }
        }

        Button(
            onClick = {
                if (
                    dateInput.isNotBlank() &&
                    managerCdInput.isNotBlank() &&
                    managerInput.isNotBlank() &&
                    temperatureInput.isNotBlank() && isFanClean && isFanClean

                ) {
                    val airConditioner = AirConditioner(
                        date = dateInput,
                        managerCd = managerCdInput,
                        manager = managerInput,
                        temperature = temperatureInput,
                        isFanClean = isFanClean,
                        isAirConditionerClean = isAirConditionerClean,
                        isIllumination = isIllumination,
                        outputFlg = 0
                    )

                    airConditionerViewModel.addAirConditioner(airConditioner)

                    dateInput = ""
                    managerCdInput = ""
                    managerInput = ""
                    temperatureInput = ""
                    isFanClean = false
                    isAirConditionerClean = false
                    isIllumination = false

                    //Toast.makeText(context, getString(R.string.navi_save_success), Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context,"入力漏れがあります", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .constrainAs(btnSave) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                }
                .fillMaxWidth()
                .padding(start = 10.dp,end = 80.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = Color.White
            )
        ) {
            Text(
                text = stringResource(R.string.btn_save),
                color = MaterialTheme.colorScheme.scrim
            )
        }

        MenuButton(
            navController = navController,
            modifier = Modifier
                .constrainAs(btnMenu) {
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .padding(12.dp)
        )
    }
}

@Composable
fun RegistrationScreen(
    navController: NavController,
    airConditionerViewModel: AirConditionerViewModel) {

    val airConditioner by airConditionerViewModel.airConditioner.collectAsState()

    LaunchedEffect(Unit) {
        try {
            airConditionerViewModel.getAllAirConditioner()
            //throw Exception("e")
        } catch (e: Exception) {
            Log.e("MyTag", "Error: ${e.message}", e)
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        val (btnMenu) = createRefs()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            ScreenTitle(
                value = stringResource(R.string.title_registration),
                modifier = Modifier
            )

            if (airConditioner.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                ) {
                    val sortedAirConditioner = airConditioner.sortedBy { it?.outputFlg }

                    items(sortedAirConditioner.size) { index ->
                        sortedAirConditioner[index]?.let {
                            AirConditionerItem(
                                airConditioner = it,
                                onLongPress = {},
                                onDoubleTap = {}
                            )
                        }
                    }
                }
            } else {
                Spacer(modifier = Modifier.height(100.dp))
                Text(text = "現在、登録されているデータはありません。",
                    modifier = Modifier
                        .padding(16.dp)
                )
            }
        }
        MenuButton(
            navController = navController,
            modifier = Modifier
                .constrainAs(btnMenu) {
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .padding(12.dp)
        )
    }
}





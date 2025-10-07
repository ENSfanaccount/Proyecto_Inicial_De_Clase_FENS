package com.jorgeromo.androidClassMp1.navigation

import SecondPartialView
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.jorgeromo.androidClassMp1.firstpartial.FirstPartialView
import com.jorgeromo.androidClassMp1.ids.imc.views.IMCView
import com.jorgeromo.androidClassMp1.ids.IdsView
import com.jorgeromo.androidClassMp1.ids.location.views.LocationListScreen
import com.jorgeromo.androidClassMp1.ids.student.views.StudentView
import com.jorgeromo.androidClassMp1.ids.sum.views.SumView
import com.jorgeromo.androidClassMp1.ids.temperature.views.TempView
import com.jorgeromo.androidClassMp1.thirdpartial.ThirdPartialView
import com.jorgeromo.androidClassMp1.firstpartial.animation.views.AnimationView
import com.jorgeromo.androidClassMp1.firstpartial.login.views.HomeView
import com.jorgeromo.androidClassMp1.firstpartial.login.views.LoginView
import com.jorgeromo.androidClassMp1.secondpartial.home.HomeRepository
import com.jorgeromo.androidClassMp1.secondpartial.home.views.HomeViewEvents
import com.jorgeromo.androidClassMp1.secondpartial.home.network.HomeApi
import com.jorgeromo.androidClassMp1.secondpartial.home.viewmodel.HomeViewModel
import com.jorgeromo.androidClassMp1.secondpartial.location.LocationCoordianteView
import com.jorgeromo.androidClassMp1.secondpartial.qrcode.view.QrCodeView
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabBarNavigationView(navController: NavHostController = rememberNavController()) {
    val items = listOf(
        ScreenNavigation.Ids,
        ScreenNavigation.FirstPartial,
        ScreenNavigation.SecondPartial,
        ScreenNavigation.ThirdPartial
    )

    val routeTitles = remember {
        mapOf(
            ScreenNavigation.Ids.route to ScreenNavigation.Ids.label,
            ScreenNavigation.FirstPartial.route to ScreenNavigation.FirstPartial.label,
            ScreenNavigation.SecondPartial.route to ScreenNavigation.SecondPartial.label,
            ScreenNavigation.ThirdPartial.route to ScreenNavigation.ThirdPartial.label,
            ScreenNavigation.IMC.route to ScreenNavigation.IMC.label,
            ScreenNavigation.Login.route to ScreenNavigation.Login.label,
            ScreenNavigation.LoginOptions.route to ScreenNavigation.LoginOptions.label,
            ScreenNavigation.Sum.route to ScreenNavigation.Sum.label,
            ScreenNavigation.Temperature.route to ScreenNavigation.Temperature.label,
            ScreenNavigation.StudentList.route to ScreenNavigation.StudentList.label,
            ScreenNavigation.Locations.route to ScreenNavigation.Locations.label,
            "LottieRoute" to "Animación Lottie",
            ScreenNavigation.HomeEvents.route to ScreenNavigation.HomeEvents.label
        )
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val currentTitle = routeTitles[currentRoute] ?: ""

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = currentTitle) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF000000),
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            NavigationBar {
                items.forEach { screen ->
                    val selected = currentRoute == screen.route
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.label) },
                        label = { Text(screen.label) },
                        selected = selected,
                        onClick = {
                            if (!selected) {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = ScreenNavigation.Ids.route,
            modifier = Modifier.padding(innerPadding)
        ) {

            composable(ScreenNavigation.Ids.route) { IdsView(navController) }
            composable(ScreenNavigation.FirstPartial.route) { FirstPartialView(navController) }
            composable(ScreenNavigation.SecondPartial.route) { SecondPartialView(navController) }
            composable(ScreenNavigation.ThirdPartial.route) { ThirdPartialView(navController) }

            // Rutas internas
            composable(ScreenNavigation.IMC.route) { IMCView() }
            composable(ScreenNavigation.Login.route) { LoginView() }
            composable(ScreenNavigation.Animation.route) { AnimationView() }
            composable(ScreenNavigation.Sum.route) { SumView() }
            composable(ScreenNavigation.Temperature.route) { TempView() }
            composable(ScreenNavigation.StudentList.route) { StudentView() }
            composable(ScreenNavigation.Locations.route) { LocationListScreen() }
            composable(ScreenNavigation.Home.route) { HomeView() }
            composable(ScreenNavigation.QrCode.route) { QrCodeView() }

            composable(ScreenNavigation.LocationCoordinate.route) {
                LocationCoordianteView()

            composable(ScreenNavigation.HomeEvents.route) {
                val viewModel: HomeViewModel = remember {
                    val retrofit = Retrofit.Builder()
                        .baseUrl("https://gist.githubusercontent.com/MiguelR222/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                    val api = retrofit.create(HomeApi::class.java)
                    val repo = HomeRepository(api)
                    HomeViewModel(repo)
                }

                val uiState by viewModel.ui.collectAsState()

                LaunchedEffect(Unit) {
                    viewModel.fetchEvents()
                }

                HomeViewEvents(uiState = uiState)


            }
        }
    }
}}

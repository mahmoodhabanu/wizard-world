package com.example.wizardworld.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.houses_domain.model.House
import com.example.houses_presentation.housedetail.HouseDetailScreen
import com.example.houses_presentation.houselist.HouseListScreen
import java.net.URLEncoder
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.HouseList.route
    ) {
        composable(route = Screen.HouseList.route) {
            HouseListScreen(
                onHouseClick = { house ->
                    // Serialize the House object to JSON and URL-encode it
                    val houseJson = URLEncoder.encode(
                        Json.encodeToString(House.serializer(), house),
                        StandardCharsets.UTF_8.toString()
                    )
                    navController.navigate(Screen.HouseDetail.createRoute(houseJson))
                }
            )
        }
        composable(
            route = Screen.HouseDetail.route,
            arguments = listOf(navArgument("houseJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val houseJson = backStackEntry.arguments?.getString("houseJson")
            val decodedHouseJson = houseJson?.let {
                URLDecoder.decode(it, StandardCharsets.UTF_8.toString())
            }
            val house = decodedHouseJson?.let {
                Json.decodeFromString(House.serializer(), it)
            }

            if (house != null) {
                HouseDetailScreen(house = house, onBackClick = { navController.popBackStack() })
            } else {
                // Handle error: House object not found or deserialization failed
                // You might navigate back or show an error message
                navController.popBackStack()
            }
        }
    }
}
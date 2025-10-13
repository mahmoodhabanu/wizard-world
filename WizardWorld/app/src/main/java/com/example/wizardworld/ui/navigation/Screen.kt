package com.example.wizardworld.ui.navigation

sealed class Screen(val route: String) {
    data object HouseList : Screen("house_list")
    data object HouseDetail : Screen("house_detail/{houseJson}") {
        fun createRoute(houseJson: String) = "house_detail/$houseJson"
    }
}
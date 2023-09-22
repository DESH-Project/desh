package com.demo.desh.ui.navigation

sealed class Screen(val route: String){
    object Main: Screen(route = "main")
    object Profile: Screen(route="profile")
}

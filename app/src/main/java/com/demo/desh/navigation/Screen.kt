package com.demo.desh.navigation

sealed class Screen(val route:String) {
    object Survey1: Screen("Survey")
    object Survey2: Screen("Survey2")
}
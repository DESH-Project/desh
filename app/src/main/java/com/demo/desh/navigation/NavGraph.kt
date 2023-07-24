package com.demo.desh.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.demo.desh.SurveyActivity
import com.demo.desh.SurveyScreen
import com.demo.desh.SurveyScreen2

//전체 네비게이션간의 도착지를 매핑
@Composable
fun NavGraph(navController: NavHostController){
    NavHost(
        navController=navController,
        startDestination = Screen.Survey1.route)
    {
        composable(route= Screen.Survey1.route){
            SurveyScreen(navController=navController)
        }
        composable(route = Screen.Survey2.route){
            SurveyScreen2(navController=navController)
        }
    }
}


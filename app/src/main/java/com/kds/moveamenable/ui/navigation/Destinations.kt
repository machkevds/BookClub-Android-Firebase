package com.kds.moveamenable.ui.navigation

//replaces nav_graph
sealed class Screen(val route: String) {
    object Main : Screen("main")
    object Workout : Screen("workout")
    object Focus : Screen("focus")
    object Info : Screen("info")

}
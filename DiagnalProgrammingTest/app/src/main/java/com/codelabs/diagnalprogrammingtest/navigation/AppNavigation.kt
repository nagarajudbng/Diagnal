package com.codelabs.diagnalprogrammingtest.navigation

enum class Screen{
    HOME,
    SEARCH
}
sealed class NavigationItem(val route:String){
    data object HOME: NavigationItem(Screen.HOME.name)
    data object SEARCH: NavigationItem(Screen.SEARCH.name)
}
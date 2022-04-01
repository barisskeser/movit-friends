package com.bbn.movitfriends.presentation.bottom_nav

import com.bbn.movitfriends.R
import com.bbn.movitfriends.presentation.Screen

sealed class BottomNavItem(val title: String, val icon: Int, val route: String) {

    object Chat: BottomNavItem("Messages", R.drawable.ic_chat, Screen.ChatScreen.route)

    object Map: BottomNavItem("Map", R.drawable.ic_map, Screen.MapScreen.route)

    data class Profile(val uid: String): BottomNavItem("Profile", R.drawable.ic_man, Screen.ProfileScreen.route + "/$uid")

}

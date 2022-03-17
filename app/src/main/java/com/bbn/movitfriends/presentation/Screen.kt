package com.bbn.movitfriends.presentation

import com.bbn.movitfriends.common.Constants

sealed class Screen(val route: String){
    object ProfileScreen: Screen("profile_screen/{${Constants.PARAM_USER_ID}}")
    object RequestScreen: Screen("request_screen")
    object SettingsScreen: Screen("settings_screen")
    object BlockListScreen: Screen("block_list_screen")
    object UpdateProfileScreen: Screen("update_profile_screen")
    object MessageScreen: Screen("message_screen/{${Constants.PARAM_USER_ID}}")
    object MapScreen: Screen("map_screen")
    object FriendScreen: Screen("friend_screen")
    object FilterScreen: Screen("filter_screen")
    object ChatScreen: Screen("chat_screen")
    object CoinScreen: Screen("coin_screen")
    object LoginScreen: Screen("login_screen")
    object RegisterScreen: Screen("register_screen")
    object GoogleRegisterScreen: Screen("google_register_screen")
}

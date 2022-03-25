package com.bbn.movitfriends.common

sealed class UiEvent {

    data class Navigate(val route: String): UiEvent()

    data class ShowError(val errorMessage: String): UiEvent()

    object ShowLoading: UiEvent()

}

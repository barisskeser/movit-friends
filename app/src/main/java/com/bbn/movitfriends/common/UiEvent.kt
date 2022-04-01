package com.bbn.movitfriends.common

sealed class UiEvent {

    data class Navigate(val route: String): UiEvent()

    object NavigatePop: UiEvent()

    data class ShowError(val errorMessage: String): UiEvent()

    object ShowLoading: UiEvent()

    object TerminateLoading: UiEvent()
}

package com.bbn.movitfriends.presentation.profile

sealed class ProfileEvent {

    data class OnActionClicked(val userUid: String): ProfileEvent()

    object OnClickEdit: ProfileEvent()

    object OnBackIconClicked: ProfileEvent()

}

package com.bbn.movitfriends.domain.repository

import com.bbn.movitfriends.domain.model.Location

interface LocationRepository {

    suspend fun getLocationOfUsers(): List<Location>

}
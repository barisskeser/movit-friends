package com.bbn.movitfriends.domain.model

import com.google.type.LatLng
import java.sql.Timestamp
import java.util.*
import kotlin.collections.HashMap

data class User(
    val status: String?,
    val id: String?,
    val username: String?,
    val fullName: String?,
    val gender: String?,
    val age: Int?,
    val email: String?,
    val lat: Double?,
    val lng: Double?,
    val isAdBlocked: Boolean?,
    val isPremium: Boolean?,
    val imageUrl: String?,
    val credit: Int?,
    val createDate: com.google.firebase.Timestamp?
)

fun User.toHashMap(): HashMap<String, Any>{
    val map = HashMap<String, Any>()
    map.put("id", this.id!!)
    map.put("username", this.username!!)
    map.put("fullName", this.fullName!!)
    map.put("gender", this.gender!!)
    map.put("age", this.age!!)
    map.put("email", this.email!!)
    map.put("lat", this.lat!!)
    map.put("lng", this.lng!!)
    map.put("isAdBlocked", this.isAdBlocked!!)
    map.put("isPremium", this.isPremium!!)
    map.put("imageUrl", this.imageUrl!!)
    map.put("credit", this.credit!!)
    map.put("createDate", this.createDate!!)

    return map
}
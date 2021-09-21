package com.jufaja.jangry01.models

import com.google.firebase.firestore.PropertyName

data class Post(
    var omschrijving: String = "",
    @get:PropertyName("image_url") @set:PropertyName("image_url") var imageUrl: String = "",
    @get:PropertyName("datum_tijd_ms") @set:PropertyName("datum_tijd_ms") var datumTijdMs: Long = 0,
    var user: User? = null
)
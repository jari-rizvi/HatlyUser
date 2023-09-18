package com.teamx.hatlyUser.ui.fragments.shophome.model
import androidx.annotation.Keep

@Keep
data class Doc(
    val _id: String,
    val count: Int,
    val documents: List<Document>
)
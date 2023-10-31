package com.teamx.hatlyUser.ui.fragments.track.socket.model.allChat

data class Doc(
    val _id: String,
    val createdAt: String,
    val entityId: String,
    val `file`: List<String>,
    val from: String,
    val isRead: Boolean,
    val message: String,
    val orderId: String,
    val socketId: String,
    val to: String,
    var isUser: Boolean,
)
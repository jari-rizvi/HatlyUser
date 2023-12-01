package com.teamx.hatlyUser.ui.fragments.track.socket.chat.model.allChat
import androidx.annotation.Keep

@Keep
data class GetAllMessageData(
    val docs: List<Doc>,
    val hasNextPage: Boolean,
    val hasPrevPage: Boolean,
    val limit: Int,
    val nextPage: Int,
    val page: Int,
    val prevPage: Any,
    val totalDocs: Int,
    val totalPages: Int
)
package com.teamx.hatlyUser.ui.fragments.track.socket.model.allChat

data class GetAllMessageData(
    val docs: List<Doc>,
    val hasNextPage: Boolean,
    val hasPrevPage: Boolean,
    val limit: Int,
    val nextPage: Any,
    val page: Int,
    val prevPage: Any,
    val totalDocs: Int,
    val totalPages: Int
)
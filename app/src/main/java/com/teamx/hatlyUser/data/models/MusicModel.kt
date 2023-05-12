package com.teamx.hatlyUser.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class MusicModel(
    @PrimaryKey
    val artistId: Int,
    val amgArtistId: Int,
    val primaryGenreId: Int,
    val wrapperType: String,
    val artistType: String,
    val artistName: String,
    val artistLinkUrl: String,
    val primaryGenreName: String,
    val kind: String,
    val collectionId: String,
    val trackId: String,
    val collectionName: String,
    val trackName: String,
    val collectionCensoredName: String,
    val trackCensoredName: String,
    val artistViewUrl: String,
    val collectionViewUrl: String,
    val trackViewUrl: String,
    val previewUrl: String,
    val artworkUrl100: String,
    var isPlaying: Boolean = false,
)
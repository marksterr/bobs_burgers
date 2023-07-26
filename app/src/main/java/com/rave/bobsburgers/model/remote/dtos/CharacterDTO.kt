package com.rave.bobsburgers.model.remote.dtos

import kotlinx.serialization.Serializable

@Serializable
data class CharacterDTO(
    val firstEpisode: String,
    val gender: String?,
    val hairColor: String,
    val id: Int,
    val image: String,
    val name: String,
    val occupation: String,
    val relatives: List<Map<String, out String>>,
    val url: String,
    val voicedBy: String,
    val wikiUrl: String
)
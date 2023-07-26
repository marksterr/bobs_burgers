package com.rave.bobsburgers.views.characterscreen

import com.rave.bobsburgers.model.local.BobCharacter

data class CharacterScreenState(
    val isLoading: Boolean = false,
    val characters: List<BobCharacter> = emptyList(),
    val error: String = ""
)
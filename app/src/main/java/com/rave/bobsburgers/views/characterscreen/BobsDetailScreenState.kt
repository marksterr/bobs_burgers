package com.rave.bobsburgers.views.characterscreen

import com.rave.bobsburgers.model.local.BobCharacter

data class BobsDetailScreenState(
    val isLoading: Boolean = false,
    val currentBob: BobCharacter? = null,
    val errormsg: String = "Error in details screen"
)

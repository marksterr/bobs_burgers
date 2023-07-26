package com.rave.bobsburgers.model.remote

import com.rave.bobsburgers.model.local.BobCharacter

sealed class NetworkResponse<T> {

    data class SuccessfulCategory(
        val characters: List<BobCharacter>
    ) : NetworkResponse<List<BobCharacter>>()

    object Loading : NetworkResponse<Unit>()

    data class Error(
        val message: String
    ) : NetworkResponse<String>()
}
package com.rave.bobsburgers.model.remote

import com.rave.bobsburgers.model.remote.dtos.CharacterResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterFetcher {

    @GET(CHARACTER_ENDPOINT)
    fun getCharacters():Call<CharacterResponse>

    companion object {
        private const val CHARACTER_ENDPOINT = "characters"
    }
}
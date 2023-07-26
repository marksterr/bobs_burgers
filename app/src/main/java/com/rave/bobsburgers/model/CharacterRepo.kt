package com.rave.bobsburgers.model

import com.rave.bobsburgers.model.local.BobCharacter
import com.rave.bobsburgers.model.mapper.CharacterMapper
import com.rave.bobsburgers.model.remote.CharacterFetcher
import com.rave.bobsburgers.model.remote.NetworkResponse
import com.rave.bobsburgers.model.remote.dtos.CharacterResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class CharacterRepo(
    private val service: CharacterFetcher,
    private val mapper: CharacterMapper
) {

    suspend fun getBobCharacters(): NetworkResponse<*> = withContext(Dispatchers.IO) {

        // val amount = "200"

        val characterResponse: Response<CharacterResponse> =
            service.getCharacters().execute()

        return@withContext if (characterResponse.isSuccessful) {
            val characterResponse = characterResponse.body() ?: CharacterResponse()
            val characterList: List<BobCharacter> = characterResponse.map {
                mapper(it)
            }
            NetworkResponse.SuccessfulCategory(characterList)
        } else {
            NetworkResponse.Error(characterResponse.message())
        }
    }
}
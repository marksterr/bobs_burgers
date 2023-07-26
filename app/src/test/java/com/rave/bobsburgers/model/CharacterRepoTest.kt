package com.rave.bobsburgers.model

import com.rave.bobsburgers.model.local.BobCharacter
import com.rave.bobsburgers.model.mapper.CharacterMapper
import com.rave.bobsburgers.model.remote.CharacterFetcher
import com.rave.bobsburgers.model.remote.NetworkResponse
import com.rave.bobsburgers.model.remote.dtos.CharacterDTO
import com.rave.bobsburgers.model.remote.dtos.CharacterResponse
import com.rave.bobsburgers.model.utilTest.CoroutinesTestExtension
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.Request
import okio.Timeout
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
internal class CharacterRepoTest {

    @RegisterExtension
    private val testExtension = CoroutinesTestExtension()
    private val service: CharacterFetcher = mockk()
    private val repo = CharacterRepo(service, CharacterMapper())

    @Test
    fun testGetCharacters() = runTest(testExtension.dispatcher) {
        val serviceResponse = CharacterResponse().apply {
            add(
                CharacterDTO(
                    firstEpisode = "test",
                    gender = "Male",
                    hairColor = "Black",
                    id = 1,
                    image = "test_image",
                    name = "Bob",
                    occupation = "Burger Chef",
                    relatives = emptyList(),
                    url = "test_url",
                    voicedBy = "H. Jon Benjamin",
                    wikiUrl = "test_wiki_url"
                )
            )
        }
        val expectedResult = listOf(
            BobCharacter(
                gender = "Male",
                id = 1,
                image = "test_image",
                name = "Bob"
            )
        )

        val call = object : Call<CharacterResponse> {
            override fun clone() = this
            override fun execute(): Response<CharacterResponse> = Response.success(serviceResponse)
            override fun enqueue(callback: Callback<CharacterResponse>?) {}
            override fun isExecuted() = true
            override fun cancel() {}
            override fun isCanceled() = false
            override fun request() = Request.Builder().build()
            override fun timeout(): Timeout {
                TODO("Not yet implemented")
            }
        }

        coEvery { service.getCharacters() } returns call

        val result = runBlocking { repo.getBobCharacters() }

        if (result is NetworkResponse.SuccessfulCategory) {
            Assertions.assertEquals(expectedResult, result.characters)
        } else {
            Assertions.fail("Network response was not SuccessfulCategory")
        }
    }
}
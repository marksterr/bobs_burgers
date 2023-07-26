package com.rave.bobsburgers.viewmodel

import com.rave.bobsburgers.model.CharacterRepo
import com.rave.bobsburgers.model.remote.NetworkResponse
import com.rave.bobsburgers.model.utilTest.CoroutinesTestExtension
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

@OptIn(ExperimentalCoroutinesApi::class)
internal class CharacterViewModelTest {

    @RegisterExtension
    private val coroutinesTestExtension = CoroutinesTestExtension()

    private val repo = mockk<CharacterRepo>()

    @Test
    @DisplayName("test getting characters from repo")
    fun testGetCharacters() {
        // Given
        val characters = NetworkResponse<*>
        coEvery { repo.getBobCharacters() } coAnswers { characters }

        // When
        val characterViewModel = CharacterViewModel(repo)

        // Then
        Assertions.assertEquals(urls, mainViewModel.urls.value)
    }
}
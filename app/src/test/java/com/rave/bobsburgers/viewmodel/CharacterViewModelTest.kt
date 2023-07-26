package com.rave.bobsburgers.viewmodel

import com.rave.bobsburgers.model.CharacterRepo
import com.rave.bobsburgers.model.local.BobCharacter
import com.rave.bobsburgers.model.remote.NetworkResponse
import com.rave.bobsburgers.model.utilTest.CoroutinesTestExtension
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
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
    fun testGetCharacters() = runTest(coroutinesTestExtension.dispatcher) {
        // Given
        val characters = listOf(
            BobCharacter(
                gender = "Male",
                id = 1,
                image = "test_image",
                name = "Bob"
            )
        )
        coEvery { repo.getBobCharacters() } returns NetworkResponse.SuccessfulCategory(characters)

        // When
        val characterViewModel = CharacterViewModel(repo)
        characterViewModel.getCharacters()

        // Then
        Assertions.assertEquals(characters, characterViewModel.characterState.value.characters)
    }
}
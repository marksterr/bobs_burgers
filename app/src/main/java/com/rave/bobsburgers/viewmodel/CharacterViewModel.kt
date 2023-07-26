package com.rave.bobsburgers.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.rave.bobsburgers.model.CharacterRepo
import com.rave.bobsburgers.model.local.BobCharacter
import com.rave.bobsburgers.model.remote.NetworkResponse
import com.rave.bobsburgers.views.characterscreen.CharacterScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterViewModel(
    private val repo: CharacterRepo
) : ViewModel() {
    private val TAG = "CharacterViewModel"

    private val _characters: MutableStateFlow<CharacterScreenState> =
        MutableStateFlow(CharacterScreenState())
    val characterState: StateFlow<CharacterScreenState> get() = _characters

    private val _searchQuery: MutableStateFlow<String> = MutableStateFlow("")
    val searchQuery: StateFlow<String> get() = _searchQuery

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun getCharacters() = viewModelScope.launch {
        _characters.update { it.copy(isLoading = true) }
        val characters = repo.getBobCharacters()
        when (characters) {
            is NetworkResponse.Error -> {
                _characters.update {
                    it.copy(
                        isLoading = false,
                        error = characters.message
                    )
                }
            }
            is NetworkResponse.SuccessfulCategory -> {
                _characters.update { it.copy(isLoading = false, characters = characters.characters) }

            }
            else -> {
                Log.e(TAG, "getCharacters: Something went wrong", )}
        }
    }

    fun getCharacterById(id: Int): BobCharacter? {
        return characterState.value.characters.find { it.id == id }
    }
}

class VMlFactory(
    private val repo: CharacterRepo
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharacterViewModel::class.java)) {
            return CharacterViewModel(repo) as T
        } else {
            throw IllegalArgumentException("Illegal Argument")
        }
    }
}
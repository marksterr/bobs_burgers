package com.rave.bobsburgers.views

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.rave.bobsburgers.model.remote.RetrofitClass
import com.rave.bobsburgers.model.CharacterRepo
import com.rave.bobsburgers.model.local.BobCharacter
import com.rave.bobsburgers.model.mapper.CharacterMapper
import com.rave.bobsburgers.ui.theme.BobsBurgersTheme
import com.rave.bobsburgers.viewmodel.CharacterViewModel
import com.rave.bobsburgers.viewmodel.VMlFactory
import com.rave.bobsburgers.views.characterscreen.BobsDetailScreen
import com.rave.bobsburgers.views.characterscreen.BobsDetailScreenState
import com.rave.bobsburgers.views.characterscreen.CharacterScreen
import com.rave.bobsburgers.views.characterscreen.CharacterScreenState

class MainActivity : ComponentActivity() {

    private val characterViewModel by viewModels<CharacterViewModel> {
        val fetcher = RetrofitClass.getCharacterFetcher()
        val repo = CharacterRepo(fetcher, CharacterMapper())
        VMlFactory(repo)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FIREBASE", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            // val msg = getString(R.string.msg_token_fmt, token)
            Log.d("FIREBASE", token)
            Toast.makeText(baseContext, "Token: $token", Toast.LENGTH_SHORT).show()
        })
        characterViewModel.getCharacters()
        setContent {
            BobsBurgersTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }

    @Composable
    fun AppNavigation() {
        val navController = rememberNavController()

        NavHost(navController, startDestination = "characters_list") {
            composable("characters_list") {
                val characterState by characterViewModel.characterState.collectAsState()
                val searchQuery by characterViewModel.searchQuery.collectAsState()
                CharacterListApp(characterState, searchQuery) { character ->
                    navController.navigate("character_details/${character.id}")
                }
            }
            composable("character_details/{characterId}") { backStackEntry ->
                val characterId = backStackEntry.arguments?.getString("characterId")
                val character = characterViewModel.getCharacterById(characterId!!.toInt())
                if (character != null) {
                    BobsDetailScreen(
                        BobsDetailScreenState(currentBob = character),
                        onBack = { navController.popBackStack() }
                    )
                } else {
                    Text("Error: character not found") // Handle the error appropriately
                }
            }
        }
    }

    @Composable
    fun CharacterListApp(state: CharacterScreenState, searchQuery: String, onClick: (BobCharacter) -> Unit){
        CharacterScreen(state, searchQuery, characterViewModel::setSearchQuery, onClick)
    }
}
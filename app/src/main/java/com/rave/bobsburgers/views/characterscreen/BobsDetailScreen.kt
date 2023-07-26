package com.rave.bobsburgers.views.characterscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.rave.bobsburgers.model.local.BobCharacter

@Composable
fun BobsDetailScreen(
    bobsDetailScreenState: BobsDetailScreenState
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxSize()
    ) {
        val bob = bobsDetailScreenState.currentBob

        bob?.let {
            AsyncImage(bob.image, contentDescription = "Lovely bob image",
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 100.dp, max = 200.dp))
            Text(text = "Name: ${bob.name}")
            Text(text = "Gender: ${bob.gender}")
        } ?: NoSuchBob()

    }
}

@Composable
fun NoSuchBob(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "no such bob")
    }
}


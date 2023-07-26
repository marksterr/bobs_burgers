package com.rave.bobsburgers.model.mapper

import com.rave.bobsburgers.model.local.BobCharacter
import com.rave.bobsburgers.model.remote.dtos.CharacterDTO

class CharacterMapper : Mapper<CharacterDTO, BobCharacter> {
    override fun invoke(dto: CharacterDTO): BobCharacter = with(dto) {
        BobCharacter(
            gender = gender ?: "Unknown",
            id = id,
            image = image,
            name = name
        )
    }
}
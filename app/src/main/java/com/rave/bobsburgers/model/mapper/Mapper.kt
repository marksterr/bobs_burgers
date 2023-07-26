package com.rave.bobsburgers.model.mapper

interface Mapper<in DTO, out ENTITY> {
    operator fun invoke(dto: DTO): ENTITY
}
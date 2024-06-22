package com.renato.nbastats.data.model

data class PlayersPaged(
    val players: ArrayList<Player>,
    val currentPage: Int,
    val totalPages: Int,
    val totalCount: Int
)
package com.renato.nbastats.data.model

import java.io.Serializable

data class Player(
    val _id: String,
    val personID: Int,
    val playerLastName: String,
    val playerFirstName: String,
    val playerImageUrl: String,
    val teamImageUrl: String,
    val playerSlug: String,
    val teamID: Int,
    val teamSlug: String,
    val isDefunct: Boolean,
    val teamCity: String,
    val teamName: String,
    val teamAbbreviation: String,
    val jerseyNumber: String,
    val position: String,
    val height: String,
    val weight: String,
    val college: String,
    val country: String,
    val draftYear: Int,
    val draftRound: Int,
    val draftNumber: Int,
    val rosterStatus: String,
    val pts: Double,
    val reb: Double,
    val ast: Double,
    val statsTimeframe: String,
    val fromYear: Int,
    val toYear: Int,
    val __v: Int
): Serializable {
    val fullName: String
        get() = "$playerFirstName $playerLastName"
}

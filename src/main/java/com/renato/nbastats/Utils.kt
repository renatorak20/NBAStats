package com.renato.nbastats

import com.renato.nbastats.data.model.Player
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object Utils {

    fun getPlayerPosition(position: String) =
        when (position) {
            "G" -> "Guard"
            "F" -> "Forward"
            "C" -> "Center"
            "C-F" -> "Center-Forward"
            "F-C" -> "Forward-Center"
            "G-F" -> "Guard-Forward"
            "F-G" -> "Forward-Guard"
            else -> "Unknown Position"
        }

    fun parseHeight(height: String): String {
        val parts = height.split("-")
        if (parts.size == 2) {
            val feet = parts[0].toIntOrNull()
            val inches = parts[1].toIntOrNull()
            if (feet != null && inches != null) {
                return "$feet'$inches\""
            }
        }
        throw IllegalArgumentException("Invalid height format")
    }

    fun parseDraft(player: Player) = "${player.draftYear} R${player.draftRound} Pick ${player.draftNumber}"

    fun getDateAndTimeFormatted(input: String): String? {
        val zonedDateTime = ZonedDateTime.parse(input)
        val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm")
        return zonedDateTime.format(formatter)
    }

}
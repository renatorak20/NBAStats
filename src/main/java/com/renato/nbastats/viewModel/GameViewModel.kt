package com.renato.nbastats.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renato.nbastats.data.model.Player
import com.renato.nbastats.data.networking.Network
import kotlinx.coroutines.launch

class GameViewModel: ViewModel() {

    private val _players = MutableLiveData<Pair<Player, Player>>()

    private fun setPlayers(player: Pair<Player, Player>) {
        _players.value = player
    }

    fun getPlayers(): MutableLiveData<Pair<Player, Player>> {
        return _players
    }

    fun fetchPlayers(homeAndAwayID: Pair<Int, Int>) {
        viewModelScope.launch {
            val homePlayer = Network().getService().getPlayerById(homeAndAwayID.first)
            val awayPlayer = Network().getService().getPlayerById(homeAndAwayID.second)

            if (homePlayer.isSuccessful && awayPlayer.isSuccessful) {
                val response = homePlayer.body()!! to awayPlayer.body()!!
                setPlayers(response)
            }
        }
    }



}
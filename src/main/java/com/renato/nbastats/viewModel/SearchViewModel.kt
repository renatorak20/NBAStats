package com.renato.nbastats.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renato.nbastats.data.model.Player
import com.renato.nbastats.data.networking.Network
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val _players = MutableLiveData<List<Player>>()

    private fun setPlayers(player: List<Player>) {
        _players.value = player
    }

    fun getPlayers(): MutableLiveData<List<Player>> {
        return _players
    }

    fun fetchPlayers(query: String) {
        viewModelScope.launch {
            val player = Network().getService().getAllPlayers(query)
            if (player.isSuccessful) {
                player.body()?.let { setPlayers(it) }
            }
        }
    }
}
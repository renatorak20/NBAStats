package com.renato.nbastats.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renato.nbastats.data.model.GameRecap
import com.renato.nbastats.data.networking.Network
import kotlinx.coroutines.launch

class PlayerViewModel : ViewModel() {

    private val _gameRecaps = MutableLiveData<List<GameRecap>>()

    private fun setRecaps(gameRecaps: List<GameRecap>) {
        _gameRecaps.value = gameRecaps
    }

    fun getRecaps(): MutableLiveData<List<GameRecap>> {
        return _gameRecaps
    }

    fun fetchRecaps(teamCode: String) {
        viewModelScope.launch {
            val recaps = Network().getService().getTeamGames(teamCode)
            if (recaps.isSuccessful) {
                recaps.body()?.let { setRecaps(it) }
            }
        }
    }

    fun fetchRecaps(epochDate: Long) {
        viewModelScope.launch {
            val recaps = Network().getService().getGamesForDate(epochDate)
            if (recaps.isSuccessful) {
                recaps.body()?.let { setRecaps(it) }
            } else {
                setRecaps(emptyList())
            }
        }
    }
}
package com.renato.nbastats

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import coil.decode.SvgDecoder
import coil.load
import com.renato.nbastats.Utils.parseDraft
import com.renato.nbastats.Utils.parseHeight
import com.renato.nbastats.data.model.Player
import com.renato.nbastats.databinding.ActivityPlayerDetailsBinding
import com.renato.nbastats.ui.adapter.GameRecapAdapter
import com.renato.nbastats.viewModel.PlayerViewModel

class PlayerDetails : AppCompatActivity() {

    private lateinit var binding: ActivityPlayerDetailsBinding
    private lateinit var playerViewModel: PlayerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        binding = ActivityPlayerDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val player = intent.extras?.get("player") as? Player

        playerViewModel = ViewModelProvider(this)[PlayerViewModel::class.java]

        player?.let {
            with(binding.playerBasicInfo) {
                playerImage.load(player.playerImageUrl)
                playerFullName.text = player.fullName
                val playerPosition = Utils.getPlayerPosition(player.position)
                teamFullName.text = player.teamCity + " " + player.teamName
                positionAndJerseyNumber.text = "$playerPosition #${player.jerseyNumber}"
                teamLogo.load(player.teamImageUrl) {
                    decoderFactory { result, options, _ -> SvgDecoder(result.source, options) }
                }
            }
            with(binding.playerStats) {
                ppg.text = player.pts.toString()
                rpg.text = player.reb.toString()
                apg.text = player.ast.toString()
            }
            with(binding.playerOtherInfo) {
                height.text = parseHeight(player.height)
                weight.text = " | ${player.weight}lb"
                draft.text = parseDraft(player)
            }
            with(binding.playerCollegeInfo) {
                college.text = player.college
                country.text = player.country
            }

            playerViewModel.fetchRecaps(player.teamAbbreviation)
        }

        playerViewModel.getRecaps().observe(this) {
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
            binding.recyclerView.adapter = it?.let { it1 ->
                GameRecapAdapter(
                    this,
                    it1.mapNotNull { data -> data.recap }.toList()
                )
            }
        }

        binding.back.setOnClickListener {
            finish()
        }
    }

}
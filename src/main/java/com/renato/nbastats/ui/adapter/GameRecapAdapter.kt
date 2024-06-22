package com.renato.nbastats.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.decode.SvgDecoder
import coil.load
import com.renato.nbastats.GameActivity
import com.renato.nbastats.R
import com.renato.nbastats.data.model.GameRecap
import com.renato.nbastats.data.model.RecapData
import com.renato.nbastats.databinding.GamePreviewItemBinding

class GameRecapAdapter(
    val context: Context,
    private val array: List<RecapData>
) : RecyclerView.Adapter<GameRecapAdapter.GameRecapViewHolder>() {

    class GameRecapViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = GamePreviewItemBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GameRecapViewHolder(
        LayoutInflater.from(context).inflate(R.layout.game_preview_item, parent, false)
    )

    override fun onBindViewHolder(holder: GameRecapViewHolder, position: Int) {
        val item = array[position]

        with(holder.binding) {
            homeTeamLayout.teamTitle.text = item.recapData?.homeTeam?.teamName
            awayTeamLayout.teamTitle.text = item.recapData?.awayTeam?.teamName
            homeTeamLayout.teamLogo.load("https://nbapi.fly.dev/team-logo/${item.recapData?.homeTeam?.teamId}") {
                decoderFactory { result, options, _ -> SvgDecoder(result.source, options) }
            }
            awayTeamLayout.teamLogo.load("https://nbapi.fly.dev/team-logo/${item.recapData?.awayTeam?.teamId}") {
                decoderFactory { result, options, _ -> SvgDecoder(result.source, options) }
            }
            homeTeamScore.text = item.recapData?.homeTeam?.score.toString()
            awayTeamScore.text = item.recapData?.awayTeam?.score.toString()

            layout.setOnClickListener {
                startGameDetailsActivity(item)
            }
        }
    }

    override fun getItemCount() = array.size

    private fun startGameDetailsActivity(recap: RecapData) {
        val intent = Intent(context, GameActivity::class.java)
        intent.putExtra("gameRecap", recap)
        context.startActivity(intent)
    }
}
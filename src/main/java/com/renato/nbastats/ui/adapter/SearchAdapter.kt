package com.renato.nbastats.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.decode.SvgDecoder
import coil.load
import com.renato.nbastats.PlayerDetails
import com.renato.nbastats.R
import com.renato.nbastats.data.model.Player
import com.renato.nbastats.databinding.PlayerSearchItemBinding

class SearchAdapter(
    val context: Context,
    private val array: List<Player>
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    class SearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = PlayerSearchItemBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            LayoutInflater.from(context).inflate(R.layout.player_search_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {

        val item = array[position]

        with(holder.binding) {
            image.load(item.playerImageUrl)
            name.text = item.fullName
            teamImage.load(item.teamImageUrl) {
                decoderFactory { result, options, _ -> SvgDecoder(result.source, options) }
            }
            teamName.text = item.teamName
            layout.setOnClickListener {
                startPlayerDetails(item)
            }
        }
    }

    override fun getItemCount() = array.size


    private fun startPlayerDetails(player: Player) {
        val intent = Intent(context, PlayerDetails::class.java)
        intent.putExtra("player", player)
        context.startActivity(intent)
    }
}
package com.renato.nbastats

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.text.font.Typeface
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.load
import coil.request.ImageRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.renato.nbastats.data.model.RecapData
import com.renato.nbastats.databinding.ActivityGameBinding
import com.renato.nbastats.viewModel.GameViewModel
import com.bumptech.glide.request.transition.Transition


class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
    private lateinit var gameViewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        gameViewModel = ViewModelProvider(this)[GameViewModel::class.java]

        val game = intent.extras?.get("gameRecap") as? RecapData
        val homeData = game?.recapData?.homeTeam
        val awayData = game?.recapData?.awayTeam

        with(binding.content.header) {
            homeTeamLayout.teamTitle.text = homeData?.teamName
            awayTeamLayout.teamTitle.text = awayData?.teamName
            homeTeamLayout.teamLogo.load("https://nbapi.fly.dev/team-logo/${homeData?.teamId}") {
                decoderFactory { result, options, _ -> SvgDecoder(result.source, options) }
            }
            awayTeamLayout.teamLogo.load("https://nbapi.fly.dev/team-logo/${awayData?.teamId}") {
                decoderFactory { result, options, _ -> SvgDecoder(result.source, options) }
            }
            homeTeamScore.text = homeData?.score.toString()
            awayTeamScore.text = awayData?.score.toString()

            if (homeData?.score!! > awayData?.score!!) {
                homeTeamScore.setTextColor(getColor(R.color.white))
                homeTeamScore.typeface = android.graphics.Typeface.DEFAULT_BOLD
            } else {
                awayTeamScore.setTextColor(getColor(R.color.white))
                awayTeamScore.typeface = android.graphics.Typeface.DEFAULT_BOLD
            }

            timeOfGame.text = Utils.getDateAndTimeFormatted(game.recapData.gameTimeUtc!!)
        }

        with(binding.content.periods) {
            val homePeriods = homeData?.periods
            homeOne.text = homePeriods?.get(0)?.score.toString()
            homeTwo.text = homePeriods?.get(1)?.score.toString()
            homeThree.text = homePeriods?.get(2)?.score.toString()
            homeFour.text = homePeriods?.get(3)?.score.toString()

            val awayPeriods = awayData?.periods
            awayOne.text = awayPeriods?.get(0)?.score.toString()
            awayTwo.text = awayPeriods?.get(1)?.score.toString()
            awayThree.text = awayPeriods?.get(2)?.score.toString()
            awayFour.text = awayPeriods?.get(3)?.score.toString()

            if (homePeriods?.get(0)?.score!! > awayPeriods?.get(0)?.score!!) {
                homeOne.setTextColor(getColor(R.color.white))
            } else if (homePeriods[0].score!! < awayPeriods[0].score!!) {
                awayOne.setTextColor(getColor(R.color.white))
            }

            if (homePeriods[1].score!! > awayPeriods[1].score!!) {
                homeTwo.setTextColor(getColor(R.color.white))
            } else if (homePeriods[1].score!! < awayPeriods[1].score!!) {
                awayTwo.setTextColor(getColor(R.color.white))
            }

            if (homePeriods[2].score!! > awayPeriods[2].score!!) {
                homeThree.setTextColor(getColor(R.color.white))
            } else if (homePeriods[2].score!! < awayPeriods[2].score!!) {
                awayThree.setTextColor(getColor(R.color.white))
            }

            if (homePeriods[3].score!! > awayPeriods[3].score!!) {
                homeFour.setTextColor(getColor(R.color.white))
            } else if (homePeriods[3].score!! < awayPeriods[3].score!!) {
                awayFour.setTextColor(getColor(R.color.white))
            }

        }

        val leadersID = homeData?.teamLeader?.personId!! to awayData?.teamLeader?.personId!!
        val leadersScore = homeData.teamLeader.points to awayData.teamLeader.points

        gameViewModel.fetchPlayers(leadersID)

        gameViewModel.getPlayers().observe(this) {
            with(binding.content.leaders) {
                homeTeamLayout.teamLogo.load(it.first.playerImageUrl)
                awayTeamLayout.teamLogo.load(it.second.playerImageUrl)
                homeTeamLayout.teamTitle.text = it.first.fullName
                awayTeamLayout.teamTitle.text = it.second.fullName

                homeTeamScore.text = leadersScore.first.toString()
                awayTeamScore.text = leadersScore.second.toString()
            }
        }

        binding.back.setOnClickListener {
            finish()
        }
    }
}
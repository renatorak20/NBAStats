package com.renato.nbastats

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.renato.nbastats.databinding.ActivityHomeBinding
import com.renato.nbastats.ui.adapter.GameRecapAdapter
import com.renato.nbastats.viewModel.GameViewModel
import com.renato.nbastats.viewModel.PlayerViewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var playerViewModel: PlayerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        playerViewModel = ViewModelProvider(this)[PlayerViewModel::class.java]

        binding.search.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }

        binding.calendar.setOnClickListener {
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()

            datePicker.show(supportFragmentManager, "tag")

            datePicker.addOnPositiveButtonClickListener {
                showIndicator()
                hideAnimation()
                playerViewModel.fetchRecaps(it / 1000L)
            }
        }

        playerViewModel.getRecaps().observe(this) {
            hideIndicator()
            if (it.isNotEmpty()) {
                hideAnimation()
                binding.recyclerView.layoutManager = LinearLayoutManager(this)
                binding.recyclerView.adapter = it?.let { it1 ->
                    GameRecapAdapter(
                        this,
                        it1.mapNotNull { data -> data.recap }.toList()
                    )
                }
            } else {
                showAnimation()
            }
        }
    }

    private fun showAnimation() {
        binding.animation.visibility = View.VISIBLE
        binding.titleNoMatches.visibility = View.VISIBLE
    }

    private fun hideAnimation() {
        binding.animation.visibility = View.GONE
        binding.titleNoMatches.visibility = View.GONE
    }

    private fun showIndicator() {
        binding.indicator.visibility = View.VISIBLE
    }
    private fun hideIndicator() {
        binding.indicator.visibility = View.GONE
    }
}
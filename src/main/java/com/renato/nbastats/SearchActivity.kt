package com.renato.nbastats

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.renato.nbastats.databinding.ActivityMainBinding
import com.renato.nbastats.ui.adapter.SearchAdapter
import com.renato.nbastats.viewModel.SearchViewModel

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var searchViewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        binding.autoComplete.threshold = 3
        binding.autoComplete.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(text: Editable?) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length!! > 3) {
                    searchViewModel.fetchPlayers(s.toString())
                } else if (s.isEmpty() || s.length < 3) {
                    resetRecyclerView()
                }
            }
        })

        binding.clearIcon.setOnClickListener {
            binding.autoComplete.setText("")
        }

        binding.back.setOnClickListener {
            finish()
        }

        searchViewModel.getPlayers().observe(this) {
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
            binding.recyclerView.adapter = SearchAdapter(this, it)
        }
    }

    private fun resetRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = SearchAdapter(this, emptyList())
    }
}
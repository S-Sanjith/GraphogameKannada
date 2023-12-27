package com.example.graphogamekannada

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.graphogamekannada.databinding.ActivityLevelsBinding

class LevelsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLevelsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLevelsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener { finish() }

        binding.level1btn.setOnClickListener {
            startGameActivity(0)
        }

        binding.level2btn.setOnClickListener {
            startGameActivity(1)
        }

        binding.level3btn.setOnClickListener {
            startGameActivity(2)
        }

        binding.level4btn.setOnClickListener {
            startGameActivity(3)
        }

        binding.level5btn.setOnClickListener {
            startGameActivity(4)
        }

        binding.level6btn.setOnClickListener {
            startGameActivity(5)
        }
    }

//    Start GameActivity by mentioning the corresponding level index (0-indexed)
    private fun startGameActivity(selectedLevelIndex: Int) {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("currentLevelIndex", selectedLevelIndex)
        startActivity(intent)
    }
}
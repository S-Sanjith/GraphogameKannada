package com.example.graphogamekannada

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.example.graphogamekannada.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startGameButton.setOnClickListener {
            val intent = Intent(this, LevelsActivity::class.java)
            startActivity(intent)
        }
        binding.aboutButton.setOnClickListener {
             val intent = Intent(this, AboutActivity::class.java)
             startActivity(intent)
        }
        binding.quitButton.setOnClickListener {
            displayQuitWindow()
        }
    }

    private fun displayQuitWindow() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.popup_quit)

        val yesButton: Button = dialog.findViewById(R.id.yesButton)
        val noButton: Button = dialog.findViewById(R.id.noButton)
        val messageTextView: TextView = dialog.findViewById(R.id.messageTextView)

        messageTextView.text = "Are you sure you want to quit?"

//        closeButton.setOnClickListener {
//            dialog.dismiss()
//        }

        yesButton.setOnClickListener {
            // Add code to navigate to the ChooseLevelsActivity or any other appropriate activity
            dialog.dismiss()
            finish()
        }

        noButton.setOnClickListener {
            // Add code to navigate to the next level or any other appropriate activity
            dialog.dismiss()
        }

        dialog.show()
    }
}
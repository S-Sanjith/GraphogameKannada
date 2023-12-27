package com.example.graphogamekannada
//
//import android.app.Dialog
//import android.media.MediaPlayer
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.view.Window
//import android.widget.Button
//import android.widget.TextView
//import android.widget.Toast
//import com.example.graphogamekannada.databinding.ActivityGameBinding
//
//class GameActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityGameBinding
//    private var mediaPlayer: MediaPlayer? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityGameBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        binding.playButton.setOnClickListener { playSound() }
//
//        binding.optionButton1.setOnClickListener { checkAnswer("ಅ") }
//        binding.optionButton2.setOnClickListener { checkAnswer("ಇ") }
//        binding.optionButton3.setOnClickListener { checkAnswer("ಎ") }
//    }
//
//    private fun playSound() {
//        val soundResourceId = R.raw.level_1
//        mediaPlayer?.release() // Release any previous resources
//
//        mediaPlayer = MediaPlayer.create(this, soundResourceId)
//
//        mediaPlayer?.setOnCompletionListener {
//            // Release resources when playback is completed
//            mediaPlayer?.release()
//            mediaPlayer = null
//        }
//
//        mediaPlayer?.start()
//    }
//
//    private fun checkAnswer(selectedOption: String) {
//        // Check the selected option against the correct answer
//        val correctAnswer = "ಇ"
//        val isCorrect = selectedOption == correctAnswer
//
//        if (isCorrect) {
//            // Handle correct answer, navigate to a different activity
////            Toast.makeText(this, "Correct! Moving to the next activity.", Toast.LENGTH_SHORT).show()
//            showCorrectAnswerPopup()
//        } else {
//            // Handle incorrect answer, allow the user to choose a different option
//            Toast.makeText(this, "Incorrect! Choose a different option.", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    private fun showCorrectAnswerPopup() {
//        val dialog = Dialog(this)
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.setCancelable(false)
//        dialog.setContentView(R.layout.popup_correct_answer)
//
//        val chooseLevelsButton: Button = dialog.findViewById(R.id.chooseLevelsButton)
//        val nextLevelButton: Button = dialog.findViewById(R.id.nextLevelButton)
//        val messageTextView: TextView = dialog.findViewById(R.id.messageTextView)
//
//        messageTextView.text = "Congratulations! You chose the correct answer."
//
////        closeButton.setOnClickListener {
////            dialog.dismiss()
////        }
//
//        chooseLevelsButton.setOnClickListener {
//            // Add code to navigate to the ChooseLevelsActivity or any other appropriate activity
//            dialog.dismiss()
//            finish()
//        }
//
//        nextLevelButton.setOnClickListener {
//            // Add code to navigate to the next level or any other appropriate activity
//            dialog.dismiss()
//        }
//
//        dialog.show()
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        mediaPlayer?.release() // Release resources when the activity is destroyed
//    }
//}


// GameActivity.kt
import android.app.Dialog
import android.media.MediaPlayer
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.graphogamekannada.databinding.ActivityGameBinding
import org.json.JSONArray
import org.json.JSONObject

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var levels: JSONArray
    private var currentLevelIndex: Int = 0
    private var correctAnswer: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve the selected level index from the Intent
        val intent = intent
        if (intent.hasExtra("currentLevelIndex")) {
            currentLevelIndex = intent.getIntExtra("currentLevelIndex", 0)
        }

        // Load levels from JSON file
        levels = loadLevels()

        binding.backButton.setOnClickListener { finish() }

        binding.playButton.setOnClickListener { playSound() }

        binding.optionButton1.setOnClickListener { checkAnswer(0) }
        binding.optionButton2.setOnClickListener { checkAnswer(1) }
        binding.optionButton3.setOnClickListener { checkAnswer(2) }

        // Start with the first level
        loadLevel(currentLevelIndex)
    }

    private fun loadLevels(): JSONArray {
        val jsonString = resources.openRawResource(R.raw.levels)
            .bufferedReader().use { it.readText() }
        return JSONObject(jsonString).getJSONArray("levels")
    }

    private fun loadLevel(levelIndex: Int) {
        val level = levels.getJSONObject(levelIndex)
        val soundResourceId = resources.getIdentifier(
            level.getString("soundResourceId"),
            "raw",
            packageName
        )

        // Set the sound for the current level
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(this, soundResourceId)

        // Set the options for the current level
        val options = level.getJSONArray("options")
        binding.optionButton1.text = options.getString(0)
        binding.optionButton2.text = options.getString(1)
        binding.optionButton3.text = options.getString(2)

        // Set the correct answer for the current level
        correctAnswer = level.getString("correctAnswer")
    }


    private fun playSound() {
        mediaPlayer?.start()
    }

    private fun checkAnswer(selectedOptionIndex: Int) {
        val button = when(selectedOptionIndex) {
            0 -> binding.optionButton1
            1 -> binding.optionButton2
            2 -> binding.optionButton3
            else -> binding.optionButton1
        }

        val isCorrect = button.text == correctAnswer

        if (isCorrect) {
            // Show a pop-up window for correct answer
            showCorrectAnswerPopup()
        } else {
            // Handle incorrect answer, allow the user to choose a different option
            Toast.makeText(this, "Incorrect! Choose a different option.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showCorrectAnswerPopup() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.popup_correct_answer)

        val chooseLevelsButton: Button = dialog.findViewById(R.id.chooseLevelsButton)
        val nextLevelButton: Button = dialog.findViewById(R.id.nextLevelButton)
        val messageTextView: TextView = dialog.findViewById(R.id.messageTextView)

        messageTextView.text = "Congratulations! You chose the correct answer."

        chooseLevelsButton.setOnClickListener {
            // Add code to navigate to the ChooseLevelsActivity or any other appropriate activity
            dialog.dismiss()
            finish()
        }

        nextLevelButton.setOnClickListener {
            // Load the next level and update the UI
            currentLevelIndex++
            if (currentLevelIndex < levels.length()) {
                loadLevel(currentLevelIndex)
            } else {
                // If there are no more levels, handle accordingly
                Toast.makeText(this, "You completed all levels!", Toast.LENGTH_SHORT).show()
                finish()
            }
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release() // Release resources when the activity is destroyed
    }
}

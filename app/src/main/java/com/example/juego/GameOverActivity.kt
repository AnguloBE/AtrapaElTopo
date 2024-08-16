package com.example.juego

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class GameOverActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game_over)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val score = intent.getIntExtra("SCORE", 0)
        val scoreTextView = findViewById<TextView>(R.id.scoreTextView)
        scoreTextView.text = "Your score: $score"

        val playAgainButton = findViewById<Button>(R.id.playAgainButton)
        val exitButton = findViewById<Button>(R.id.exitButton)

        playAgainButton.setOnClickListener {
            // Inicia una nueva partida
            val intent = Intent(this, viewJuego::class.java)
            startActivity(intent)
            finish()
        }

        exitButton.setOnClickListener {
            // Salir del juego
            finish()
        }
    }
}
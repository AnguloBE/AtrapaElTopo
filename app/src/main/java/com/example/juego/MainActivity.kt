package com.example.juego

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btnJugar = findViewById<Button>(R.id.btnJugar)
        val btnAjustes = findViewById<Button>(R.id.btnAjustes)
        val btnLeaderboard = findViewById<Button>(R.id.btnLeaderboard)

        btnJugar.setOnClickListener {
            val intent = Intent(this@MainActivity,viewJuego::class.java)
            startActivity(intent)
        }

        btnAjustes.setOnClickListener {
            val intent = Intent(this@MainActivity,ajustes::class.java)
            startActivity(intent)
        }

        btnLeaderboard.setOnClickListener {
            val intent = Intent(this@MainActivity,Leaderboard::class.java)
            startActivity(intent)
        }

    }
}
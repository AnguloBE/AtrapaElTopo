package com.example.juego

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ajustes : AppCompatActivity() {

    private lateinit var iv1: ImageView
    private lateinit var iv2: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajustes)

        iv1 = findViewById(R.id.iv1)
        iv2 = findViewById(R.id.iv2)
        val btnCamera = findViewById<Button>(R.id.btn1)

        // Inicializa imágenes predeterminadas
        iv1.setImageResource(R.drawable.topo)
        iv2.setImageResource(R.drawable.topomalo)


        btnCamera.setOnClickListener {
            Toast.makeText(this, "Proximamente", Toast.LENGTH_SHORT).show()
        }
    }
}

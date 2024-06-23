package com.example.juego

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

class viewJuego : AppCompatActivity() {

    private lateinit var moleGrid: Array<Array<Mole>>
    private lateinit var game: Game
    private val random = Random
    private val handler = Handler(Looper.getMainLooper())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_view_juego)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val scoreTextView = findViewById<TextView>(R.id.textView7)
        scoreTextView.text = "Score: 0"
        val timerTextView = findViewById<TextView>(R.id.textView6)
        timerTextView.text = "Time: 0 s"
        game = Game(scoreTextView, timerTextView)

        val gridLayout = findViewById<GridLayout>(R.id.gridLayout)


        // Inicializar la matriz de Mole
        val rowCount = gridLayout.rowCount
        val columnCount = gridLayout.columnCount
        moleGrid = Array(columnCount) { col ->
            Array(rowCount) { row ->
                val imageView = gridLayout.getChildAt(row * columnCount + col) as ImageView
                Mole(imageView, game)
            }
        }

        startMoleAppearance()
        game.startTimer()
    }

    private fun startMoleAppearance() {
        val runnable = object : Runnable {
            override fun run() {
                // Elegir una celda aleatoria
                val row = random.nextInt(moleGrid.size)
                val col = random.nextInt(moleGrid[0].size)
                val mole = moleGrid[row][col]

                // Configurar el topo como GOOD_MOLE o BAD_MOLE aleatoriamente
                if (random.nextBoolean()) {
                    if(mole.type== MoleType.empty){
                        mole.setGoodMole()
                    }
                } else {
                    if(mole.type == MoleType.empty){
                        mole.setBadMole()
                    }
                }

                // Reprogramar la aparición del próximo topo
                handler.postDelayed(this, 400) // Ajusta el tiempo de aparición como desees
            }
        }

        handler.post(runnable)
    }
}
package com.example.juego

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.widget.TextView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.firestore.FirebaseFirestore

class Game(
    private val scoreTextView: TextView,
    private val timerTextView: TextView,
    private val context: Context
) {

    private var score: Int = 0
    private lateinit var countDownTimer: CountDownTimer
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun addPoints(points: Int) {
        score += points
        updateScoreDisplay()
    }

    fun updateScoreDisplay() {
        scoreTextView.text = "Score: $score"
    }

    fun startTimer() {
        countDownTimer = object : CountDownTimer(6000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                timerTextView.text = "Time: $secondsRemaining s"
            }

            override fun onFinish() {
                endGame()
            }
        }

        countDownTimer.start()
    }

    private fun endGame() {
        timerTextView.text = "Time: 0 s"
        uploadScoreToFirebase()

        // Crear Intent para iniciar GameOverActivity
        val intent = Intent(context, GameOverActivity::class.java)
        intent.putExtra("SCORE", score)
        context.startActivity(intent)

        // Si el contexto es una actividad, finaliza la actividad principal
        if (context is Activity) {
            (context as Activity).finish()
        }
    }

    fun uploadScoreToFirebase() {
        val account = GoogleSignIn.getLastSignedInAccount(context)
        if (account != null) {
            val userId = account.id ?: "Unknown"
            val userEmail = account.email ?: "Unknown"

            // Referencia al documento del usuario en la base de datos Firestore
            val userScoreRef = db.collection("scores").document(userId)

            // Leer el puntaje actual en la base de datos
            userScoreRef.get().addOnSuccessListener { document ->
                val currentScore = document.getLong("score")?.toInt() ?: 0

                // Solo actualizar si el nuevo puntaje es mayor que el actual
                if (score > currentScore) {
                    // Crear un objeto de datos para guardar en Firebase
                    val scoreData = mapOf(
                        "userId" to userId,
                        "userEmail" to userEmail,
                        "score" to score
                    )

                    // Subir los datos a Firebase bajo un documento único para cada usuario
                    userScoreRef.set(scoreData)
                        .addOnSuccessListener {
                            // Puntaje subido exitosamente
                        }
                        .addOnFailureListener {
                            // Manejar fallos al subir datos
                        }
                }
            }.addOnFailureListener {
                // Manejar fallos al obtener el puntaje actual
            }
        }
    }
}

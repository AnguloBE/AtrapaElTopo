package com.example.juego

import android.os.CountDownTimer
import android.widget.TextView

class Game(private val scoreTextView: TextView, private val timerTextView: TextView) {

    private var score: Int = 0
    private lateinit var countDownTimer: CountDownTimer

    fun addPoints(points: Int){
        score += points
        updateScoreDisplay()
    }

    fun updateScoreDisplay(){
        scoreTextView.text = "Score: $score"
    }

    fun startTimer() {
        countDownTimer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished /1000
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
    }
}
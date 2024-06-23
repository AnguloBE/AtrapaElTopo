package com.example.juego

import android.os.Handler
import android.os.Looper
import android.widget.ImageView

enum class MoleType {
    empty,
    goodMole,
    badMole
}

class Mole(private val imageView: ImageView, private val game: Game) {

    var type: MoleType = MoleType.empty
    private set

    private val handler = Handler(Looper.getMainLooper())
    private val hideRunnable = Runnable { setEmpty() }

    init {
        imageView.setOnClickListener {
            when (type){
                MoleType.goodMole -> game.addPoints(10)
                MoleType.badMole -> game.addPoints(-5)
                MoleType.empty -> {/*no pasa nada papu*/}
            }
            setEmpty()
        }
    }

    fun setEmpty(){
        type= MoleType.empty
        imageView.setImageResource(R.drawable.agujero)
        handler.removeCallbacks(hideRunnable)
    }

    fun setGoodMole() {
        type = MoleType.goodMole
        imageView.setImageResource(R.drawable.topo)
        scheduleHide()
    }

    fun setBadMole() {
        type = MoleType.badMole
        imageView.setImageResource(R.drawable.topomalo)
        scheduleHide()
    }

    private fun scheduleHide() {
        handler.removeCallbacks(hideRunnable)
        handler.postDelayed(hideRunnable, 2500)
    }
}
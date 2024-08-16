package com.example.juego

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class Leaderboard : AppCompatActivity() {

    private lateinit var leaderboardRecyclerView: RecyclerView
    private lateinit var leaderboardAdapter: LeaderboardAdapter
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_leaderboard)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        leaderboardRecyclerView = findViewById(R.id.leaderboardRecyclerView)
        leaderboardRecyclerView.layoutManager = LinearLayoutManager(this)

        // Obtener los datos de la base de datos
        db.collection("scores")
            .orderBy("score", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents ->
                val leaderboardList = mutableListOf<LeaderboardEntry>()
                for (document in documents) {
                    val userEmail = document.getString("userEmail") ?: "Unknown"
                    val score = document.getLong("score")?.toInt() ?: 0
                    leaderboardList.add(LeaderboardEntry(userEmail, score))
                }
                leaderboardAdapter = LeaderboardAdapter(leaderboardList)
                leaderboardRecyclerView.adapter = leaderboardAdapter
            }
            .addOnFailureListener {
                // Manejar fallos al obtener datos
            }
    }
}
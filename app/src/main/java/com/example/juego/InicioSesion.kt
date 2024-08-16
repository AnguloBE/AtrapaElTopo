package com.example.juego

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class InicioSesion : AppCompatActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inicio_sesion)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configurar Google Sign-In para solicitar el correo electrónico
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        // Crear un GoogleSignInClient con las opciones especificadas por gso
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Verificar si ya existe una cuenta con sesión iniciada
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account != null) {
            // Si ya hay una sesión iniciada, redirige a la siguiente actividad
            goToNextActivity()
        }

        // Configurar el botón de inicio de sesión
        val btnInicioSesion = findViewById<Button>(R.id.btnInicioSesion)
        btnInicioSesion.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            // Inicio de sesión exitoso, redirigir a la siguiente actividad
            goToNextActivity()
        } catch (e: ApiException) {
            // Manejar errores de inicio de sesión
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
        }
    }

    private fun goToNextActivity() {
        // Inicia la siguiente actividad después de un inicio de sesión exitoso
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Finaliza esta actividad para que no se pueda regresar a ella
    }

    companion object {
        private const val RC_SIGN_IN = 9001
        private const val TAG = "InicioSesion"
    }
}

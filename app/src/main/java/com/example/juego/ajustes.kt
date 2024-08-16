package com.example.juego

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class ajustes : AppCompatActivity() {

    private lateinit var iv1: ImageView
    private lateinit var iv2: ImageView

    private val REQUEST_CODE_PERMISSIONS = 1001
    private val PICK_IMAGE_REQUEST = 1002

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var pickImageLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajustes)

        iv1 = findViewById(R.id.iv1)
        iv2 = findViewById(R.id.iv2)
        val btnCamera = findViewById<Button>(R.id.btn1)
        val btnGallery = findViewById<Button>(R.id.btn2)

        // Inicializa imágenes predeterminadas
        iv1.setImageResource(R.drawable.topo)
        iv2.setImageResource(R.drawable.topomalo)

        // Configura el listener para el botón de la galería para iv1
        btnCamera.setOnClickListener {
            openGalleryForImage1()
        }

        // Configura el listener para el botón de la galería para iv2
        btnGallery.setOnClickListener {
            openGalleryForImage2()
        }

        // Inicializa los lanzadores de actividades
        requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions[Manifest.permission.READ_EXTERNAL_STORAGE] == true &&
                permissions[Manifest.permission.WRITE_EXTERNAL_STORAGE] == true) {
                // Permisos concedidos
                // Realizar acción que requiere permisos
            } else {
                // Permisos denegados
            }
        }

        pickImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val selectedImageUri = result.data?.data
                if (selectedImageUri != null) {
                    // Determina qué botón fue presionado para asignar la imagen correcta
                    if (result.data?.hasExtra("button1") == true) {
                        iv1.setImageURI(selectedImageUri)
                    } else if (result.data?.hasExtra("button2") == true) {
                        iv2.setImageURI(selectedImageUri)
                    }
                }
            }
        }

        // Solicita permisos
        checkPermissions()
    }

    private fun checkPermissions() {
        val readPermission = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        if (readPermission != PackageManager.PERMISSION_GRANTED) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                requestPermissionLauncher.launch(arrayOf(Manifest.permission.READ_MEDIA_IMAGES))
            } else {
                requestPermissionLauncher.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
            }
        }
    }

    private fun openGalleryForImage1() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.putExtra("button1", true) // Indicador de qué botón fue presionado
        pickImageLauncher.launch(intent)
    }

    private fun openGalleryForImage2() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.putExtra("button2", true) // Indicador de qué botón fue presionado
        pickImageLauncher.launch(intent)
    }
}

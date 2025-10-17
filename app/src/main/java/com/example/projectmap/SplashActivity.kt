package com.example.projectmap // <-- GANTI DENGAN NAMA PACKAGE ANDA

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashActivity : AppCompatActivity() {

    // Waktu tunda splash screen dalam milidetik
    private val SPLASH_DELAY: Long = 3000 // 3 detik

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Sembunyikan ActionBar/Toolbar jika ada
        supportActionBar?.hide()

        // Handler untuk menjalankan kode setelah waktu tunda
        Handler(Looper.getMainLooper()).postDelayed({
            // Pindah ke MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            // Tutup SplashActivity agar tidak bisa kembali ke layar ini
            finish()
        }, SPLASH_DELAY)
    }
}
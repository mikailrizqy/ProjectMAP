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
            val prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE)
            val username = prefs.getString("username", null)

            if (username != null) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }

            finish()
        }, SPLASH_DELAY)
    }
}
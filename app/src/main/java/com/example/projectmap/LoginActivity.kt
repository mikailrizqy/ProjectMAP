package com.example.projectmap

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class LoginActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        val etUsername = findViewById<EditText>(R.id.et_username)
        val etPassword = findViewById<EditText>(R.id.et_password)
        val btnLogin = findViewById<Button>(R.id.btn_login)
        val btnRegister = findViewById<Button>(R.id.btn_go_register)

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            val savedUsername = prefs.getString("username", null)
            val savedPassword = prefs.getString("password", null)

            // === LOGIKA UNTUK MASUK KE HALAMAN ADMIN ===
            if (username == "admin" && password == "admin123") { // Contoh kredensial admin
                Toast.makeText(this, "Login Berhasil sebagai Admin!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, AdminActivity::class.java) // Pindah ke AdminActivity
                startActivity(intent)
                finish() // Menutup LoginActivity agar tidak bisa kembali dengan tombol back
            }
            // === LOGIKA UNTUK MASUK KE HALAMAN UTAMA (USER BIASA) ===
            else if (username == "user" && password == "user123") { // Contoh kredensial user biasa
                Toast.makeText(this, "Login Berhasil sebagai Pengguna!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java) // Pindah ke MainActivity
                startActivity(intent)
                finish()
            }
            // === LOGIKA JIKA GAGAL LOGIN ===
            else {
                Toast.makeText(this, "Username atau Password salah.", Toast.LENGTH_SHORT).show()
            }
        }

        btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}

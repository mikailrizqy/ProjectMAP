package com.example.projectmap // <-- PASTIKAN NAMA PACKAGE INI SESUAI DENGAN PROYEK ANDA

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val description: String,
    val imageResId: Int // Ini akan menampung ID gambar dari folder drawable
)
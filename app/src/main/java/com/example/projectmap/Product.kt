package com.example.projectmap // <-- PASTIKAN NAMA PACKAGE INI SESUAI DENGAN PROYEK ANDA

import android.os.Parcelable
import kotlinx.parcelize.Parcelize // <<< TAMBAHKAN IMPORT INI

@Parcelize // <<< TAMBAHKAN ANOTASI INI
data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val description: String,
    val imageResId: Int // Ini akan menampung ID gambar dari folder drawable
) : Parcelable // <<< TAMBAHKAN ": Parcelable"
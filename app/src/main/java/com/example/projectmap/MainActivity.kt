package com.example.projectmap // <-- PASTIKAN NAMA PACKAGE INI SESUAI DENGAN PROYEK ANDA

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Menyiapkan Toolbar
        // Kita akan menggunakan Toolbar yang telah kita definisikan di XML
        setSupportActionBar(findViewById(R.id.toolbar_main))

        // 2. Menyiapkan RecyclerView
        val rvProducts: RecyclerView = findViewById(R.id.rv_products)
        rvProducts.layoutManager = LinearLayoutManager(this) // Atur layout manager (misal: linear/vertikal)

        // 3. Membuat dan Menetapkan Adapter
        // Kita panggil fungsi untuk mendapatkan data dummy dan memasukkannya ke adapter
        val productAdapter = ProductAdapter(getDummyProductData())
        rvProducts.adapter = productAdapter
    }

    // Fungsi untuk membuat data produk dummy
    private fun getDummyProductData(): List<Product> {
        val productList = mutableListOf<Product>()

        // Tambahkan produk dummy ke dalam list
        // GANTI 'R.drawable.nama_file_gambar' dengan nama file gambar Anda
        productList.add(
            Product(
                id = 1,
                name = "Cangkul Baja Modern",
                price = 150000.0,
                description = "Cangkul baja super kuat dan anti karat, cocok untuk semua jenis tanah.",
                imageResId = R.drawable.cangkul_baja // GANTI INI
            )
        )
        productList.add(
            Product(
                id = 2,
                name = "Traktor Mini Serbaguna",
                price = 25000000.0,
                description = "Traktor mini untuk membajak sawah dengan efisien dan cepat.",
                imageResId = R.drawable.traktor_mini // GANTI INI
            )
        )
        productList.add(
            Product(
                id = 3,
                name = "Semprotan Hama Elektrik",
                price = 450000.0,
                description = "Semprotan hama dengan tenaga baterai, kapasitas 16 liter.",
                imageResId = R.drawable.semprotan_hama // GANTI INI
            )
        )
        productList.add(
            Product(
                id = 4,
                name = "Sekop Tangan Premium",
                price = 75000.0,
                description = "Sekop tangan ergonomis untuk pekerjaan kebun dan pertanian skala kecil.",
                imageResId = R.drawable.sekop_tangan // GANTI INI, jika punya gambar ke-4
            )
        )

        return productList
    }
}
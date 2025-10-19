package com.example.projectmap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONObject

class DetailProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_product)

        // Mengatur Toolbar
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Menampilkan tombol kembali
        supportActionBar?.title = "" // Kosongkan judul agar bisa diisi nama produk

        // Mengambil data produk dari Intent
        val productId = intent.getIntExtra("PRODUCT_ID", -1)
        val productName = intent.getStringExtra("PRODUCT_NAME")
        val productPrice = intent.getDoubleExtra("PRODUCT_PRICE", 0.0)
        val productDescription = intent.getStringExtra("PRODUCT_DESCRIPTION")
        val productImageResId = intent.getIntExtra("PRODUCT_IMAGE_RES_ID", R.drawable.ic_launcher_background)

        // Menghubungkan View dengan data
        val ivDetailImage: ImageView = findViewById(R.id.iv_detail_image)
        val tvDetailName: TextView = findViewById(R.id.tv_detail_name)
        val tvDetailPrice: TextView = findViewById(R.id.tv_detail_price)
        val tvDetailDescription: TextView = findViewById(R.id.tv_detail_description)
        val btnAddToCart: Button = findViewById(R.id.btn_add_to_cart)

        // HAPUS BARIS INI karena tombol sudah tidak ada di layout
        // val btnUploadImagePlaceholder: Button = findViewById(R.id.btn_upload_image_placeholder)

        ivDetailImage.setImageResource(productImageResId)
        tvDetailName.text = productName
        tvDetailPrice.text = "Rp ${String.format("%,.0f", productPrice)}"
        tvDetailDescription.text = productDescription

        // Mengatur judul toolbar sesuai nama produk
        supportActionBar?.title = productName

        // HAPUS listener untuk btnUploadImagePlaceholder karena tombol sudah tidak ada
        // btnUploadImagePlaceholder.setOnClickListener {
        //     Toast.makeText(this, "Simulasi membuka kamera/galeri untuk ${productName}", Toast.LENGTH_SHORT).show()
        // }

        // Listener untuk tombol "Tambah ke Keranjang"
        btnAddToCart.setOnClickListener {
            addToCart(
                Product(
                    id = productId,
                    name = productName ?: "Produk",
                    price = productPrice,
                    description = productDescription ?: "",
                    imageResId = productImageResId
                )
            )
            Toast.makeText(this, "$productName ditambahkan ke keranjang!", Toast.LENGTH_SHORT).show()
        }

        // RecyclerView untuk Rekomendasi ML (dengan data dummy)
        val rvMlRecommendations: RecyclerView = findViewById(R.id.rv_ml_recommendations)
        rvMlRecommendations.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvMlRecommendations.adapter = ProductAdapter(getDummyRecommendationData()) { /* Tidak melakukan apa-apa saat item rekomendasi diklik */ }
    }

    private fun addToCart(product: Product) {
        val sharedPref = getSharedPreferences("CartData", MODE_PRIVATE)
        val jsonString = sharedPref.getString("cart", "[]")
        val jsonArray = JSONArray(jsonString)

        val newItem = JSONObject().apply {
            put("id", product.id)
            put("name", product.name)
            put("price", product.price)
            put("description", product.description)
            put("imageResId", product.imageResId)
        }

        jsonArray.put(newItem)
        sharedPref.edit().putString("cart", jsonArray.toString()).apply()
    }

    // Fungsi untuk membuat data rekomendasi dummy - TIDAK DIUBAH
    private fun getDummyRecommendationData(): List<Product> {
        // Ini bisa diisi dengan beberapa produk dummy lain atau produk yang sudah ada
        // Untuk demo UTS, kita bisa hardcode saja
        val recommendationList = mutableListOf<Product>()
        recommendationList.add(
            Product(
                id = 5,
                name = "Pupuk Organik Premium",
                price = 50000.0,
                description = "Pupuk organik alami untuk hasil panen melimpah.",
                imageResId = R.drawable.pupuk_organik // GANTI INI DENGAN GAMBAR ANDA
            )
        )
        recommendationList.add(
            Product(
                id = 6,
                name = "Alat Tanam Benih Otomatis",
                price = 1200000.0,
                description = "Memudahkan penanaman benih dengan presisi tinggi.",
                imageResId = R.drawable.alat_tanam // GANTI INI DENGAN GAMBAR ANDA
            )
        )
        // Tambahkan lebih banyak jika Anda punya gambar
        return recommendationList
    }

    // Mengatur agar tombol back di toolbar berfungsi
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
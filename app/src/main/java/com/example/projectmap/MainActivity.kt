package com.example.projectmap // <-- PASTIKAN NAMA PACKAGE INI SESUAI DENGAN PROYEK ANDA

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmap.DetailProductActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar_main))

        val rvProducts: RecyclerView = findViewById(R.id.rv_products)
        rvProducts.layoutManager = LinearLayoutManager(this)

        // MODIFIKASI BARU: Buat fungsi untuk menangani klik item
        val itemClickListener: (Product) -> Unit = { product ->
            val intent = Intent(this, DetailProductActivity::class.java).apply {
                putExtra("PRODUCT_ID", product.id)
                putExtra("PRODUCT_NAME", product.name)
                putExtra("PRODUCT_PRICE", product.price)
                putExtra("PRODUCT_DESCRIPTION", product.description)
                putExtra("PRODUCT_IMAGE_RES_ID", product.imageResId)
            }
            startActivity(intent)
        }

        // MODIFIKASI BARU: Lewatkan itemClickListener ke ProductAdapter
        val productAdapter = ProductAdapter(getDummyProductData(), itemClickListener)
        rvProducts.adapter = productAdapter
    }

    private fun getDummyProductData(): List<Product> {
        val productList = mutableListOf<Product>()
        productList.add(
            Product(
                id = 1,
                name = "Cangkul Baja Modern",
                price = 150000.0,
                description = "Cangkul baja super kuat dan anti karat, cocok untuk semua jenis tanah. Sangat ideal untuk pekerjaan di kebun dan lahan pertanian kecil.",
                imageResId = R.drawable.cangkul_baja
            )
        )
        productList.add(
            Product(
                id = 2,
                name = "Traktor Mini Serbaguna",
                price = 25000000.0,
                description = "Traktor mini handal dan efisien untuk membajak sawah dan mengolah lahan. Dilengkapi fitur modern untuk kemudahan operasional.",
                imageResId = R.drawable.traktor_mini
            )
        )
        productList.add(
            Product(
                id = 3,
                name = "Semprotan Hama Elektrik",
                price = 450000.0,
                description = "Semprotan hama otomatis dengan baterai tahan lama dan kapasitas 16 liter. Memudahkan penyemprotan tanpa perlu memompa manual.",
                imageResId = R.drawable.semprotan_hama
            )
        )
        productList.add(
            Product(
                id = 4,
                name = "Sekop Tangan Premium",
                price = 75000.0,
                description = "Sekop tangan dengan desain ergonomis, material kuat, dan nyaman digenggam. Cocok untuk menanam, menggali, dan membersihkan area kecil.",
                imageResId = R.drawable.sekop_tangan
            )
        )

        return productList
    }
}
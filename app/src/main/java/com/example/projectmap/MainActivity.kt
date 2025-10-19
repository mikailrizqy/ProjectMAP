package com.example.projectmap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var productAdapter: ProductAdapter
    private lateinit var rvProducts: RecyclerView
    private lateinit var etSearch: EditText
    private lateinit var cartButton: ImageButton
    private lateinit var bottomNavigation: BottomNavigationView

    private val originalProductList = mutableListOf<Product>()
    private val filteredProductList = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup Toolbar
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""

        // Initialize views
        etSearch = findViewById(R.id.et_search)
        cartButton = findViewById(R.id.cart_button)
        rvProducts = findViewById(R.id.rv_products)
        bottomNavigation = findViewById(R.id.bottom_navigation)

        // Setup Bottom Navigation
        setupBottomNavigation()

        // Setup RecyclerView untuk Home
        rvProducts.layoutManager = LinearLayoutManager(this)

        // Get product data
        originalProductList.clear()
        originalProductList.addAll(getDummyProductData())
        filteredProductList.clear()
        filteredProductList.addAll(originalProductList)

        // Setup adapter
        productAdapter = ProductAdapter(filteredProductList) { product ->
            // Handle product click - navigate to detail
            val intent = android.content.Intent(this, DetailProductActivity::class.java).apply {
                putExtra("PRODUCT_ID", product.id)
                putExtra("PRODUCT_NAME", product.name)
                putExtra("PRODUCT_PRICE", product.price)
                putExtra("PRODUCT_DESCRIPTION", product.description)
                putExtra("PRODUCT_IMAGE_RES_ID", product.imageResId)
            }
            startActivity(intent)
        }
        rvProducts.adapter = productAdapter

        // Setup search functionality
        setupSearch()

        // Setup cart button
        cartButton.setOnClickListener {
            // Navigate to cart activity
            val intent = android.content.Intent(this, CartActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Membuka keranjang", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupBottomNavigation() {
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    showHome()
                    true
                }
                R.id.menu_notifications -> {
                    showFragment(NotificationsFragment())
                    true
                }
                R.id.menu_transactions -> {
                    showFragment(TransactionsFragment())
                    true
                }
                R.id.menu_account -> {
                    showFragment(AccountFragmentMain())
                    true
                }
                else -> false
            }
        }

        // Set default selected item
        bottomNavigation.selectedItemId = R.id.menu_home
    }

    private fun showHome() {
        // Tampilkan RecyclerView dan sembunyikan fragment
        rvProducts.visibility = RecyclerView.VISIBLE
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, Fragment()) // Empty fragment
            .commit()
    }

    private fun showFragment(fragment: Fragment) {
        // Sembunyikan RecyclerView dan tampilkan fragment
        rvProducts.visibility = RecyclerView.GONE
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun setupSearch() {
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                filterProducts(s.toString())
            }
        })
    }

    private fun filterProducts(query: String) {
        filteredProductList.clear()

        if (query.isEmpty()) {
            // Jika search kosong, tampilkan semua produk
            filteredProductList.addAll(originalProductList)
        } else {
            // Filter produk berdasarkan nama (case insensitive)
            val searchQuery = query.toLowerCase().trim()
            for (product in originalProductList) {
                if (product.name.toLowerCase().contains(searchQuery)) {
                    filteredProductList.add(product)
                }
            }
        }

        // Update adapter
        productAdapter.updateData(filteredProductList)

        // Optional: Tampilkan pesan jika tidak ada hasil
        if (filteredProductList.isEmpty() && query.isNotEmpty()) {
            Toast.makeText(this, "Tidak ada produk dengan nama '$query'", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getDummyProductData(): List<Product> {
        return listOf(
            Product(
                id = 1,
                name = "Cangkul Baja Modern",
                price = 150000.0,
                description = "Cangkul baja super kuat dan anti karat, cocok untuk semua jenis tanah.",
                imageResId = R.drawable.cangkul_baja
            ),
            Product(
                id = 2,
                name = "Traktor Mini Serbaguna",
                price = 25000000.0,
                description = "Traktor mini handal dan efisien untuk membajak sawah dan mengolah lahan.",
                imageResId = R.drawable.traktor_mini
            ),
            Product(
                id = 3,
                name = "Semprotan Hama Elektrik",
                price = 450000.0,
                description = "Semprotan hama otomatis dengan baterai tahan lama dan kapasitas 16 liter.",
                imageResId = R.drawable.semprotan_hama
            ),
            Product(
                id = 4,
                name = "Sekop Tangan Premium",
                price = 75000.0,
                description = "Sekop tangan dengan desain ergonomis, material kuat, dan nyaman digenggam.",
                imageResId = R.drawable.sekop_tangan
            ),
            Product(
                id = 5,
                name = "Pupuk Organik Premium",
                price = 50000.0,
                description = "Pupuk organik alami untuk hasil panen melimpah.",
                imageResId = R.drawable.pupuk_organik
            ),
            Product(
                id = 6,
                name = "Alat Tanam Benih Otomatis",
                price = 1200000.0,
                description = "Memudahkan penanaman benih dengan presisi tinggi.",
                imageResId = R.drawable.alat_tanam
            )
        )
    }
}
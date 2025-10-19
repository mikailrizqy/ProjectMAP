package com.example.projectmap // <-- PASTIKAN NAMA PACKAGE INI SESUAI DENGAN PROYEK ANDA

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View // Import View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AlertDialog
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import android.app.Activity
import androidx.fragment.app.Fragment

class AdminActivity : AppCompatActivity() {

    private lateinit var adminProductAdapter: AdminProductAdapter
    private var productList: MutableList<Product> = mutableListOf()
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var rvAdminProducts: RecyclerView
    private lateinit var fabAddProduct: FloatingActionButton
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar // Deklarasi toolbar

    private val addEditProductLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.getBooleanExtra("PRODUCT_MODIFIED", false)?.let { modified ->
                    if (modified) {
                        productList = getDummyProductData().toMutableList()
                        adminProductAdapter.updateData(productList)
                        Toast.makeText(this, "Daftar produk diperbarui.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        toolbar = findViewById(R.id.toolbar_admin)
        setSupportActionBar(toolbar)

        // Inisialisasi komponen
        rvAdminProducts = findViewById(R.id.rv_admin_products)
        fabAddProduct = findViewById(R.id.fab_add_product)
        bottomNavigation = findViewById(R.id.bottom_navigation)

        // Setup Bottom Navigation
        setupBottomNavigation()

        // Load default fragment (Edit/Product Management)
        if (savedInstanceState == null) { // Hanya saat pertama kali dibuka
            bottomNavigation.selectedItemId = R.id.menu_edit // Set default ke tab "Edit"
        }

        // Handle tombol back device
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

                // Perbaiki typo di sini: EmptyFragmen
                if (currentFragment != null && currentFragment !is EmptyFragment) {
                    // Jika sedang di fragment lain, kembali ke Produk (Edit)
                    bottomNavigation.selectedItemId = R.id.menu_edit
                } else {
                    // Jika sudah di Produk, keluar
                    finish()
                }
            }
        })
    }

    // Setup Bottom Navigation
    private fun setupBottomNavigation() {
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_edit -> { // ID dari menu.xml
                    showProductManagement()
                    true
                }
                R.id.nav_admin_orders -> { // ID BARU DITAMBAHKAN
                    showOrdersAdminFragment() // Panggil fungsi baru
                    true
                }
                R.id.menu_dashboard -> { // ID dari menu.xml
                    showDashboardFragment()
                    true
                }
                R.id.menu_account -> { // ID dari menu.xml
                    showAccountFragment()
                    true
                }
                else -> false
            }
        }
    }

    private fun showProductManagement() {
        supportActionBar?.title = "Manajemen Produk (Admin)"

        // Tampilkan RecyclerView dan FAB
        rvAdminProducts.visibility = View.VISIBLE
        fabAddProduct.visibility = View.VISIBLE

        // Setup RecyclerView
        rvAdminProducts.layoutManager = LinearLayoutManager(this)
        productList = getDummyProductData().toMutableList()

        if (!::adminProductAdapter.isInitialized) { // Cek jika adapter belum dibuat
            adminProductAdapter = AdminProductAdapter(
                productList,
                onEditClickListener = { product ->
                    val intent = Intent(this, AddEditProductActivity::class.java).apply {
                        putExtra("PRODUCT_EDIT", product)
                    }
                    addEditProductLauncher.launch(intent)
                },
                onDeleteClickListener = { product ->
                    showDeleteConfirmationDialog(product)
                }
            )
            rvAdminProducts.adapter = adminProductAdapter
        } else {
            adminProductAdapter.updateData(productList) // Jika sudah ada, update datanya
        }

        // Setup FAB
        fabAddProduct.setOnClickListener {
            val intent = Intent(this, AddEditProductActivity::class.java)
            addEditProductLauncher.launch(intent)
        }

        // Sembunyikan fragment container dengan memuat EmptyFragmen
        // Perbaiki typo di sini: EmptyFragmen
        loadFragment(EmptyFragment())
    }

    // FUNGSI BARU UNTUK ORDERS
    private fun showOrdersAdminFragment() {
        supportActionBar?.title = "Daftar Pesanan"

        // Sembunyikan RecyclerView dan FAB
        rvAdminProducts.visibility = View.GONE
        fabAddProduct.visibility = View.GONE

        // Tampilkan fragment OrdersAdminFragment
        loadFragment(OrdersAdminFragment()) // Gunakan nama kelas yang benar
    }


    private fun showDashboardFragment() {
        supportActionBar?.title = "Dashboard Penjualan"

        // Sembunyikan RecyclerView dan FAB
        rvAdminProducts.visibility = View.GONE
        fabAddProduct.visibility = View.GONE

        // Tampilkan fragment dashboard
        loadFragment(DashboardFragment()) // Gunakan nama kelas yang benar
    }

    private fun showAccountFragment() {
        supportActionBar?.title = "Profil Toko"

        // Sembunyikan RecyclerView dan FAB
        rvAdminProducts.visibility = View.GONE
        fabAddProduct.visibility = View.GONE

        // Tampilkan fragment account
        loadFragment(AccountFragment()) // Gunakan nama kelas yang benar
    }

    // Fungsi helper untuk memuat fragment
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment) // Pastikan R.id.fragment_container ada di activity_admin.xml
            .commit()
    }

    // ... (Fungsi showDeleteConfirmationDialog dan getDummyProductData tetap sama) ...

    private fun showDeleteConfirmationDialog(product: Product) {
        AlertDialog.Builder(this)
            .setTitle("Hapus Produk")
            .setMessage("Apakah Anda yakin ingin menghapus produk '${product.name}'?")
            .setPositiveButton("Hapus") { dialog, which ->
                productList.remove(product)
                adminProductAdapter.updateData(productList)
                Toast.makeText(this, "${product.name} berhasil dihapus!", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Batal") { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }

    private fun getDummyProductData(): List<Product> {
        return listOf(
            Product(
                id = 1,
                name = "Cangkul Baja Modern",
                price = 150000.0,
                description = "Cangkul baja super kuat dan anti karat, cocok untuk semua jenis tanah. Sangat ideal untuk pekerjaan di kebun dan lahan pertanian kecil.",
                imageResId = R.drawable.cangkul_baja
            ),
            Product(
                id = 2,
                name = "Traktor Mini Serbaguna",
                price = 25000000.0,
                description = "Traktor mini handal dan efisien untuk membajak sawah dan mengolah lahan. Dilengkapi fitur modern untuk kemudahan operasional.",
                imageResId = R.drawable.traktor_mini
            ),
            Product(
                id = 3,
                name = "Semprotan Hama Elektrik",
                price = 450000.0,
                description = "Semprotan hama otomatis dengan baterai tahan lama dan kapasitas 16 liter. Memudahkan penyemprotan tanpa perlu memompa manual.",
                imageResId = R.drawable.semprotan_hama
            ),
            Product(
                id = 4,
                name = "Sekop Tangan Premium",
                price = 75000.0,
                description = "Sekop tangan dengan desain ergonomis, material kuat, dan nyaman digenggam. Cocok untuk menanam, menggali, dan membersihkan area kecil.",
                imageResId = R.drawable.sekop_tangan
            )
        )
    }
}
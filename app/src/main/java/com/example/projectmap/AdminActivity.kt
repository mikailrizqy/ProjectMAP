package com.example.projectmap // <-- PASTIKAN NAMA PACKAGE INI SESUAI DENGAN PROYEK ANDA

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AlertDialog
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import android.app.Activity // Pastikan ini juga ada

class AdminActivity : AppCompatActivity() {

    private lateinit var adminProductAdapter: AdminProductAdapter
    private var productList: MutableList<Product> = mutableListOf() // Daftar produk yang bisa dimodifikasi

    private val addEditProductLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // Jika ada perubahan, refresh daftar produk
                result.data?.getBooleanExtra("PRODUCT_MODIFIED", false)?.let { modified ->
                    if (modified) {
                        // Untuk sekarang, kita hanya akan me-refresh dari data dummy
                        // Di implementasi nyata, Anda akan memuat ulang dari database
                        productList = getDummyProductData().toMutableList() // Re-load data dummy
                        adminProductAdapter.updateData(productList)
                        Toast.makeText(this, "Daftar produk diperbarui.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_admin)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Tombol kembali di toolbar
        supportActionBar?.title = "Manajemen Produk (Admin)"

        // Inisialisasi RecyclerView
        val rvAdminProducts: RecyclerView = findViewById(R.id.rv_admin_products)
        rvAdminProducts.layoutManager = LinearLayoutManager(this)

        // Dapatkan data produk (saat ini dari MainActivity, nanti bisa dari database)
        productList = getDummyProductData().toMutableList() // Pastikan ini mutable

        // Inisialisasi AdminProductAdapter
        adminProductAdapter = AdminProductAdapter(
            productList,
            onEditClickListener = { product ->
                // === BAGIAN YANG DIKOREKSI (onEditClickListener) ===
                val intent = Intent(this, AddEditProductActivity::class.java).apply {
                    putExtra("PRODUCT_EDIT", product) // Kirim objek produk untuk diedit
                }
                addEditProductLauncher.launch(intent) // Memanggil AddEditProductActivity
                // ===================================================
            },
            onDeleteClickListener = { product ->
                // Aksi saat tombol Delete diklik
                showDeleteConfirmationDialog(product)
            }
        )
        rvAdminProducts.adapter = adminProductAdapter

        // Mengatur FloatingActionButton (FAB) untuk menambah produk
        val fabAddProduct: FloatingActionButton = findViewById(R.id.fab_add_product)
        fabAddProduct.setOnClickListener {
            // === BAGIAN YANG DIKOREKSI (fabAddProduct) ===
            val intent = Intent(this, AddEditProductActivity::class.java)
            addEditProductLauncher.launch(intent) // Memanggil AddEditProductActivity
            // =============================================
        }
    }

    // Fungsi untuk menampilkan dialog konfirmasi hapus
    private fun showDeleteConfirmationDialog(product: Product) {
        AlertDialog.Builder(this)
            .setTitle("Hapus Produk")
            .setMessage("Apakah Anda yakin ingin menghapus produk '${product.name}'?")
            .setPositiveButton("Hapus") { dialog, which ->
                // Logika untuk menghapus produk dari daftar
                productList.remove(product)
                adminProductAdapter.updateData(productList) // Perbarui adapter
                Toast.makeText(this, "${product.name} berhasil dihapus!", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Batal") { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }

    // Mengambil data dummy (bisa sama dengan MainActivity atau dari sumber lain)
    private fun getDummyProductData(): List<Product> {
        // Untuk saat ini, kita bisa menggunakan data dummy yang sama dari MainActivity
        // Nantinya, data ini bisa diambil dari database atau Shared Preferences
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
            )
            ,
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

    // Fungsi untuk tombol kembali di toolbar
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
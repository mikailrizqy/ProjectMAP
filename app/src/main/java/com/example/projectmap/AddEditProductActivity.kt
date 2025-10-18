package com.example.projectmap // <-- PASTIKAN NAMA PACKAGE INI SESUAI DENGAN PROYEK ANDA

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.textfield.TextInputEditText

class AddEditProductActivity : AppCompatActivity() {

    private lateinit var ivProductPreview: ImageView
    private lateinit var etProductName: TextInputEditText
    private lateinit var etProductPrice: TextInputEditText
    private lateinit var etProductDescription: TextInputEditText
    private var currentImageUri: Uri? = null // Untuk menyimpan URI gambar yang dipilih/diambil

    private var editingProduct: Product? = null // Jika null, berarti mode "Tambah Produk"

    // Kode request untuk memilih gambar dari galeri
    private val PICK_IMAGE_REQUEST = 1
    // Kode request untuk mengambil gambar dari kamera
    private val CAPTURE_IMAGE_REQUEST = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_product)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_add_edit)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Tambah Produk" // Default, akan diubah jika mode edit

        // Inisialisasi Views
        ivProductPreview = findViewById(R.id.iv_product_preview)
        etProductName = findViewById(R.id.et_product_name)
        etProductPrice = findViewById(R.id.et_product_price)
        etProductDescription = findViewById(R.id.et_product_description)
        val btnUploadImage: Button = findViewById(R.id.btn_upload_image)
        val btnSaveProduct: Button = findViewById(R.id.btn_save_product)

        // Cek apakah ada data produk yang dikirim (berarti mode Edit)
        // Pastikan nama Extra "PRODUCT_EDIT" sama persis seperti saat mengirimnya
        intent.getParcelableExtra<Product>("PRODUCT_EDIT")?.let { product ->
            editingProduct = product
            supportActionBar?.title = "Edit Produk"
            // Isi formulir dengan data produk yang akan diedit
            etProductName.setText(product.name)
            etProductPrice.setText(product.price.toString())
            etProductDescription.setText(product.description)
            ivProductPreview.setImageResource(product.imageResId) // Untuk gambar bawaan (drawable)
            // Jika Anda ingin menampilkan gambar dari URI (misal hasil kamera), Anda perlu logika tambahan
        }

        // Listener untuk tombol unggah gambar
        btnUploadImage.setOnClickListener {
            showImagePickerDialog() // Panggil dialog untuk pilihan kamera/galeri
        }

        // Listener untuk tombol simpan produk
        btnSaveProduct.setOnClickListener {
            saveProduct()
        }
    }

    // Dialog untuk memilih sumber gambar (kamera/galeri)
    private fun showImagePickerDialog() {
        val options = arrayOf("Ambil Foto dari Kamera", "Pilih dari Galeri")
        AlertDialog.Builder(this)
            .setTitle("Pilih Sumber Gambar")
            .setItems(options) { dialog, which ->
                when (which) {
                    0 -> openCamera()
                    1 -> openGallery()
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    // Fungsi untuk membuka galeri
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
        Toast.makeText(this, "Membuka galeri...", Toast.LENGTH_SHORT).show()
    }

    // Fungsi untuk membuka kamera
    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Untuk UTS, kita hanya simulasikan. Biasanya di sini kita siapkan file untuk output gambar
        startActivityForResult(intent, CAPTURE_IMAGE_REQUEST)
        Toast.makeText(this, "Membuka kamera...", Toast.LENGTH_SHORT).show()
    }

    // Fungsi untuk menangani hasil dari kamera/galeri
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PICK_IMAGE_REQUEST -> {
                    currentImageUri = data?.data
                    ivProductPreview.setImageURI(currentImageUri) // Menampilkan gambar dari galeri
                    Toast.makeText(this, "Gambar dari galeri dipilih!", Toast.LENGTH_SHORT).show()
                }
                CAPTURE_IMAGE_REQUEST -> {
                    // Gambar dari kamera biasanya langsung ada di data intent sebagai thumbnail
                    // Untuk gambar ukuran penuh, perlu logika penyimpanan file terlebih dahulu
                    val imageBitmap = data?.extras?.get("data") as? android.graphics.Bitmap
                    if (imageBitmap != null) {
                        ivProductPreview.setImageBitmap(imageBitmap) // Menampilkan gambar dari kamera
                        // currentImageUri = ... perlu disimpan URI jika mau diupload
                        Toast.makeText(this, "Gambar dari kamera diambil!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Gagal mengambil gambar dari kamera.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            Toast.makeText(this, "Pencarian gambar dibatalkan.", Toast.LENGTH_SHORT).show()
        }
    }

    // Fungsi untuk menyimpan/memperbarui produk
    private fun saveProduct() {
        val name = etProductName.text.toString().trim()
        val priceString = etProductPrice.text.toString().trim()
        val description = etProductDescription.text.toString().trim()

        if (name.isEmpty() || priceString.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Mohon lengkapi semua data produk.", Toast.LENGTH_SHORT).show()
            return
        }

        val price = priceString.toDoubleOrNull()
        if (price == null) {
            Toast.makeText(this, "Harga tidak valid.", Toast.LENGTH_SHORT).show()
            return
        }

        // Simulasikan penyimpanan
        val message = if (editingProduct == null) {
            "Produk '$name' berhasil ditambahkan!"
        } else {
            "Produk '$name' berhasil diperbarui!"
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()

        // Setelah berhasil menyimpan, kita akan kembali ke AdminActivity
        // Dan mengirimkan sinyal bahwa daftar produk perlu diperbarui
        val resultIntent = Intent().apply {
            // Anda bisa menambahkan data produk baru/teredit di sini jika perlu
            putExtra("PRODUCT_MODIFIED", true) // Indikator bahwa ada perubahan
        }
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    // Fungsi untuk tombol kembali di toolbar
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
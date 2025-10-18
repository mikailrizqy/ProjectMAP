package com.example.projectmap // <-- PASTIKAN NAMA PACKAGE INI SESUAI DENGAN PROYEK ANDA

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Adapter untuk halaman admin, dengan listener untuk edit dan delete
class AdminProductAdapter(
    private val productList: MutableList<Product>, // Menggunakan MutableList karena data bisa berubah
    private val onEditClickListener: (Product) -> Unit,
    private val onDeleteClickListener: (Product) -> Unit
) : RecyclerView.Adapter<AdminProductAdapter.AdminProductViewHolder>() {

    class AdminProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.iv_admin_product_image)
        val nameView: TextView = itemView.findViewById(R.id.tv_admin_product_name)
        val priceView: TextView = itemView.findViewById(R.id.tv_admin_product_price)
        val editButton: ImageButton = itemView.findViewById(R.id.btn_edit_product)
        val deleteButton: ImageButton = itemView.findViewById(R.id.btn_delete_product)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_admin_product, parent, false)
        return AdminProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdminProductViewHolder, position: Int) {
        val currentProduct = productList[position]

        holder.imageView.setImageResource(currentProduct.imageResId)
        holder.nameView.text = currentProduct.name
        holder.priceView.text = "Rp ${String.format("%,.0f", currentProduct.price)}"

        // Set listener untuk tombol Edit
        holder.editButton.setOnClickListener {
            onEditClickListener(currentProduct)
        }

        // Set listener untuk tombol Delete
        holder.deleteButton.setOnClickListener {
            onDeleteClickListener(currentProduct)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    // Fungsi untuk memperbarui daftar produk di adapter (akan berguna saat tambah/hapus)
    fun updateData(newProductList: List<Product>) {
        productList.clear()
        productList.addAll(newProductList)
        notifyDataSetChanged() // Memberitahu RecyclerView bahwa data telah berubah
    }
}
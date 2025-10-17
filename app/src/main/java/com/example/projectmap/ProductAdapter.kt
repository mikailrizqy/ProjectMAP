package com.example.projectmap // <-- PASTIKAN NAMA PACKAGE INI SESUAI DENGAN PROYEK ANDA

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// MODIFIKASI BARU: Tambahkan parameter onClickListener
class ProductAdapter(
    private val productList: List<Product>,
    private val onClickListener: (Product) -> Unit // Fungsi lambda untuk menangani klik
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.iv_product_image)
        val nameView: TextView = itemView.findViewById(R.id.tv_product_name)
        val priceView: TextView = itemView.findViewById(R.id.tv_product_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentProduct = productList[position]

        holder.imageView.setImageResource(currentProduct.imageResId)
        holder.nameView.text = currentProduct.name
        holder.priceView.text = "Rp ${String.format("%,.0f", currentProduct.price)}"

        // MODIFIKASI BARU: Set OnClickListener untuk seluruh item View
        holder.itemView.setOnClickListener {
            onClickListener(currentProduct) // Panggil fungsi yang diterima dari konstruktor
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}
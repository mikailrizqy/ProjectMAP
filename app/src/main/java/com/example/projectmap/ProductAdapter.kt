package com.example.projectmap // <-- GANTI DENGAN NAMA PACKAGE ANDA

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Pastikan nama kelasnya sudah benar: ProductAdapter
class ProductAdapter(private val productList: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    // Pastikan nama inner classnya benar: ProductViewHolder
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
        // Pastikan format harga sudah benar
        holder.priceView.text = "Rp ${String.format("%,.0f", currentProduct.price)}"
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}
package com.example.projectmap

data class Order(
    val orderId: String,
    val customerName: String,
    val totalAmount: Double,
    val status: String, // Contoh: "Pending", "Diproses", "Selesai", "Dibatalkan"
    val orderDate: String, // Contoh: "2023-10-26"
    val productCount: Int // Jumlah item produk dalam pesanan
)
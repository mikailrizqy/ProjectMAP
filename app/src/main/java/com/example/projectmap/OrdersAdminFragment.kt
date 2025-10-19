package com.example.projectmap

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OrdersAdminFragment : Fragment() {

    private lateinit var orderAdapter: OrderAdapter
    private var orderList: MutableList<Order> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_orders_admin, container, false)

        val rvOrders: RecyclerView = view.findViewById(R.id.rv_orders)
        rvOrders.layoutManager = LinearLayoutManager(context)

        orderAdapter = OrderAdapter(orderList)
        rvOrders.adapter = orderAdapter

        loadOrderData() // Memuat data pesanan dummy

        return view
    }

    private fun loadOrderData() {
        // Bersihkan daftar lama dan tambahkan data dummy
        orderList.clear()
        orderList.addAll(getDummyOrderData())
        orderAdapter.notifyDataSetChanged()
    }

    // Fungsi untuk menghasilkan data pesanan dummy
    private fun getDummyOrderData(): List<Order> {
        return listOf(
            Order("ORD001", "Budi Santoso", 1250000.0, "Pending", "2023-10-25", 2),
            Order("ORD002", "Citra Lestari", 300000.0, "Diproses", "2023-10-24", 1),
            Order("ORD003", "Agus Wijaya", 4500000.0, "Selesai", "2023-10-23", 3),
            Order("ORD004", "Dewi Sartika", 75000.0, "Pending", "2023-10-22", 1),
            Order("ORD005", "Rahmat Hidayat", 25000000.0, "Selesai", "2023-10-21", 1),
            Order("ORD006", "Siti Aminah", 150000.0, "Diproses", "2023-10-20", 1),
            Order("ORD007", "Joko Susilo", 900000.0, "Pending", "2023-10-19", 2),
            Order("ORD008", "Sri Wahyuni", 600000.0, "Dibatalkan", "2023-10-18", 1)
        )
    }
}
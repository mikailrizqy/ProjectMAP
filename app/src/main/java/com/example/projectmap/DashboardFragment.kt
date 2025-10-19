package com.example.projectmap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class DashboardFragment : Fragment() { // Pastikan nama kelas ini DashboardFragment

    private lateinit var reviewAdapter: ReviewAdapter
    private var reviewList: MutableList<Review> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false) // Pastikan R.layout.fragment_dashboard

        // Inisialisasi TextView untuk ringkasan penjualan dan produk terlaris
        val tvTotalSales: TextView = view.findViewById(R.id.tv_total_sales)
        val tvBestSeller: TextView = view.findViewById(R.id.tv_best_seller)

        // Set data dummy untuk ringkasan (nantinya ini akan diisi dari data dinamis)
        tvTotalSales.text = "Rp 12.450.000"
        tvBestSeller.text = "Cangkul Baja Modern"

        // Setup RecyclerView untuk ulasan pembeli
        val rvReviews: RecyclerView = view.findViewById(R.id.rv_reviews)
        rvReviews.layoutManager = LinearLayoutManager(context) // Menggunakan context dari Fragment

        reviewAdapter = ReviewAdapter(reviewList)
        rvReviews.adapter = reviewAdapter

        loadReviewData() // Memuat data dummy untuk ulasan

        return view
    }

    private fun loadReviewData() {
        reviewList.clear()
        reviewList.addAll(getDummyReviewData())
        reviewAdapter.notifyDataSetChanged()
    }

    // Fungsi dummy untuk data ulasan
    private fun getDummyReviewData(): List<Review> {
        return listOf(
            Review("Budi Santoso", "Cangkul Baja Modern", 5.0f, "Kuat dan tajam! Pengiriman cepat."),
            Review("Citra Lestari", "Traktor Mini Serbaguna", 4.5f, "Sangat membantu, tapi bensinnya agak boros."),
            Review("Agus Wijaya", "Sekop Tangan Premium", 4.0f, "Bagus, tapi gagangnya licin."),
            Review("Dewi Sartika", "Semprotan Hama Elektrik", 5.0f, "Mudah digunakan dan efektif basmi hama!"),
            Review("Rahmat Hidayat", "Cangkul Baja Modern", 3.0f, "Standar, tidak ada yang istimewa.")
        )
    }
}
package com.example.projectmap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment

class TransactionsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_transactions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Tidak perlu handle button click untuk sekarang
        // Atau Anda bisa tambahkan logic jika diperlukan nanti

        Toast.makeText(requireContext(), "Menampilkan riwayat transaksi", Toast.LENGTH_SHORT).show()
    }
}
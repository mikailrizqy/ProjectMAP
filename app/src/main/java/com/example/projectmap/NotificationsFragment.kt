package com.example.projectmap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class NotificationsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvClearAll = view.findViewById<TextView>(R.id.tv_clear_all)

        tvClearAll.setOnClickListener {
            // Logic untuk menghapus semua notifikasi
            Toast.makeText(requireContext(), "Semua notifikasi dihapus", Toast.LENGTH_SHORT).show()
        }

        // Anda bisa menambahkan click listener untuk setiap item notifikasi di sini
    }
}
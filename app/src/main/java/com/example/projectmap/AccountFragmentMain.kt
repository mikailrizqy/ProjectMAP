package com.example.projectmap

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

class AccountFragmentMain : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnLogout = view.findViewById<Button>(R.id.btn_logout)
        val btnEditProfile = view.findViewById<Button>(R.id.btn_edit_profile)

        // Handle tombol edit profil
        btnEditProfile.setOnClickListener {
            showEditProfileDialog()
        }


        // Handle tombol logout
        btnLogout.setOnClickListener {
            showLogoutConfirmation()
        }

        // Handle klik item notifikasi
        view.findViewById<View>(R.id.notification_item)?.setOnClickListener {
            Toast.makeText(requireContext(), "Pengaturan notifikasi", Toast.LENGTH_SHORT).show()
        }

        // Handle klik item bahasa
        view.findViewById<View>(R.id.language_item)?.setOnClickListener {
            Toast.makeText(requireContext(), "Pilih bahasa", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showEditProfileDialog() {
        // Untuk demo, tampilkan dialog sederhana
        Toast.makeText(requireContext(), "Fitur edit profil akan datang", Toast.LENGTH_SHORT).show()

        // Nanti bisa diganti dengan Intent ke EditProfileActivity
        // val intent = Intent(requireContext(), EditProfileActivity::class.java)
        // startActivity(intent)
    }

    private fun showPhotoOptionsDialog() {
        val options = arrayOf("Ambil Foto", "Pilih dari Galeri", "Hapus Foto")

        AlertDialog.Builder(requireContext())
            .setTitle("Ubah Foto Profil")
            .setItems(options) { dialog, which ->
                when (which) {
                    0 -> Toast.makeText(requireContext(), "Membuka kamera", Toast.LENGTH_SHORT).show()
                    1 -> Toast.makeText(requireContext(), "Membuka galeri", Toast.LENGTH_SHORT).show()
                    2 -> Toast.makeText(requireContext(), "Foto dihapus", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun showLogoutConfirmation() {
        AlertDialog.Builder(requireContext())
            .setTitle("Logout")
            .setMessage("Apakah Anda yakin ingin logout?")
            .setPositiveButton("Ya") { dialog, which ->
                logout()
            }
            .setNegativeButton("Tidak", null)
            .show()
    }

    private fun logout() {
        // Clear user data dan kembali ke login
        val intent = Intent(requireContext(), LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        requireActivity().finish()

        Toast.makeText(requireContext(), "Berhasil logout", Toast.LENGTH_SHORT).show()
    }
}
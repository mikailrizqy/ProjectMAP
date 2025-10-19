package com.example.projectmap

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AccountFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnLogout = view.findViewById<MaterialButton>(R.id.btn_logout)
        val btnSaveProfile = view.findViewById<MaterialButton>(R.id.btn_save_profile)

        btnSaveProfile.setOnClickListener {
            // Simpan perubahan profil
            showSaveConfirmation()
        }

        btnLogout.setOnClickListener {
            showLogoutConfirmation()
        }
    }

    private fun showSaveConfirmation() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Simpan Perubahan")
            .setMessage("Apakah Anda yakin ingin menyimpan perubahan profil toko?")
            .setPositiveButton("Simpan") { dialog, which ->
                // Simpan ke database atau shared preferences
                // Untuk sekarang tampilkan toast
                android.widget.Toast.makeText(
                    requireContext(),
                    "Profil berhasil disimpan",
                    android.widget.Toast.LENGTH_SHORT
                ).show()
            }
            .setNegativeButton("Batal") { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }

    private fun showLogoutConfirmation() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Logout")
            .setMessage("Apakah Anda yakin ingin logout?")
            .setPositiveButton("Ya") { dialog, which ->
                logout()
            }
            .setNegativeButton("Tidak") { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }

    private fun logout() {
        val intent = Intent(requireContext(), LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        requireActivity().finish()
    }
}
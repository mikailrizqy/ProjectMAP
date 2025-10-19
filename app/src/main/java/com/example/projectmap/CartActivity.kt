package com.example.projectmap

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONObject

class CartActivity : AppCompatActivity() {

    private lateinit var rvCart: RecyclerView
    private lateinit var btnCheckout: Button
    private lateinit var tvTotalPrice: TextView
    private lateinit var cartContent: LinearLayout
    private lateinit var fragmentContainer: android.widget.FrameLayout

    private val cartList = mutableListOf<Product>()
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        rvCart = findViewById(R.id.rv_cart)
        btnCheckout = findViewById(R.id.btn_checkout)
        tvTotalPrice = findViewById(R.id.tv_total_price)
        cartContent = findViewById(R.id.cart_content)
        fragmentContainer = findViewById(R.id.fragment_container)

        rvCart.layoutManager = LinearLayoutManager(this)
        adapter = ProductAdapter(cartList) { /* tidak perlu klik */ }
        rvCart.adapter = adapter

        loadCartData()
        updateTotalPrice()

        btnCheckout.setOnClickListener {
            if (cartList.isEmpty()) {
                Toast.makeText(this, "Keranjang kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Calculate total price
            val totalPrice = cartList.sumOf { it.price }

            // Show payment fragment
            showPaymentFragment(totalPrice)
        }

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_cart)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Keranjang Belanja"
    }

    override fun onSupportNavigateUp(): Boolean {
        // Jika di payment fragment, kembali ke cart
        if (fragmentContainer.visibility == View.VISIBLE) {
            showCartContent()
            return true
        }
        // Jika di cart, tutup activity
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun showPaymentFragment(totalPrice: Double) {
        // Hide cart content and show fragment container
        cartContent.visibility = View.GONE
        fragmentContainer.visibility = View.VISIBLE

        // Update toolbar title
        supportActionBar?.title = "Pembayaran"

        // Show payment fragment
        val paymentFragment = PaymentFragment.newInstance(totalPrice)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, paymentFragment)
            .commit()
    }

    fun showCartContent() {
        // Show cart content and hide fragment container
        fragmentContainer.visibility = View.GONE
        cartContent.visibility = View.VISIBLE

        // Update toolbar title
        supportActionBar?.title = "Keranjang Belanja"

        // Reload cart data (in case items were removed)
        loadCartData()
        updateTotalPrice()
    }

    private fun loadCartData() {
        val sharedPref = getSharedPreferences("CartData", Context.MODE_PRIVATE)
        val jsonString = sharedPref.getString("cart", "[]")

        val jsonArray = JSONArray(jsonString)
        cartList.clear()

        for (i in 0 until jsonArray.length()) {
            val item = jsonArray.getJSONObject(i)
            cartList.add(
                Product(
                    id = item.getInt("id"),
                    name = item.getString("name"),
                    price = item.getDouble("price"),
                    description = item.getString("description"),
                    imageResId = item.getInt("imageResId")
                )
            )
        }
        adapter.notifyDataSetChanged()
    }

    private fun updateTotalPrice() {
        val total = cartList.sumOf { it.price }
        tvTotalPrice.text = "Total: Rp ${String.format("%,.0f", total)}"
    }

    private fun clearCart() {
        val sharedPref = getSharedPreferences("CartData", Context.MODE_PRIVATE)
        sharedPref.edit().putString("cart", "[]").apply()
    }

    // Fungsi untuk membersihkan keranjang setelah pembayaran berhasil
    fun onPaymentSuccess() {
        clearCart()
        Toast.makeText(this, "Pembayaran berhasil!", Toast.LENGTH_SHORT).show()
        finish()
    }
}
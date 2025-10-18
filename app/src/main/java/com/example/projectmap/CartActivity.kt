package com.example.projectmap

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
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

    private val cartList = mutableListOf<Product>()
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        rvCart = findViewById(R.id.rv_cart)
        btnCheckout = findViewById(R.id.btn_checkout)
        tvTotalPrice = findViewById(R.id.tv_total_price)

        rvCart.layoutManager = LinearLayoutManager(this)
        adapter = ProductAdapter(cartList) { /* tidak perlu klik */ }
        rvCart.adapter = adapter

        loadCartData()
        updateTotalPrice()

        btnCheckout.setOnClickListener {
            Toast.makeText(this, "Checkout berhasil (simulasi)!", Toast.LENGTH_SHORT).show()
            clearCart()
            finish()
        }

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_cart)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Keranjang Belanja"

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
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
}

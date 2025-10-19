package com.example.projectmap

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.util.*

class OrderAdapter(private var orderList: List<Order>) :
    RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvOrderId: TextView = itemView.findViewById(R.id.tv_order_id)
        val tvCustomerName: TextView = itemView.findViewById(R.id.tv_customer_name)
        val tvOrderDate: TextView = itemView.findViewById(R.id.tv_order_date)
        val tvTotalAmount: TextView = itemView.findViewById(R.id.tv_total_amount)
        val tvStatus: TextView = itemView.findViewById(R.id.tv_status)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orderList[position]
        holder.tvOrderId.text = "Order #${order.orderId}"
        holder.tvCustomerName.text = "Pembeli: ${order.customerName}"
        holder.tvOrderDate.text = "Tanggal: ${order.orderDate}"

        // Format total amount ke mata uang Rupiah
        val formatRupiah = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        holder.tvTotalAmount.text = formatRupiah.format(order.totalAmount)

        holder.tvStatus.text = order.status

        // Contoh: Mengatur warna background status (bisa dikembangkan lebih lanjut)
        when (order.status) {
            "Pending" -> holder.tvStatus.setBackgroundResource(R.drawable.bg_status_pending)
            "Diproses" -> holder.tvStatus.setBackgroundResource(R.drawable.bg_status_processing) // Anda perlu buat drawable ini
            "Selesai" -> holder.tvStatus.setBackgroundResource(R.drawable.bg_status_completed) // Anda perlu buat drawable ini
            "Dibatalkan" -> holder.tvStatus.setBackgroundResource(R.drawable.bg_status_cancelled) // Anda perlu buat drawable ini
            else -> holder.tvStatus.setBackgroundResource(R.drawable.bg_status_pending)
        }
    }

    override fun getItemCount(): Int = orderList.size

    fun updateData(newOrderList: List<Order>) {
        orderList = newOrderList
        notifyDataSetChanged()
    }
}
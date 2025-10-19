package com.example.projectmap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class PaymentFragment : Fragment() {

    private var totalPrice: Double = 0.0
    private val adminFee: Double = 2500.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get total price from arguments
        totalPrice = arguments?.getDouble("TOTAL_PRICE", 0.0) ?: 0.0

        setupViews(view)
        setupPaymentMethods(view)
        setupBackButton(view)
    }

    private fun setupBackButton(view: View) {
        val btnBack = view.findViewById<ImageView>(R.id.btn_back)
        btnBack.setOnClickListener {
            // Kembali ke cart
            (requireActivity() as? CartActivity)?.showCartContent()
        }
    }

    private fun setupViews(view: View) {
        val tvSubtotal = view.findViewById<TextView>(R.id.tv_subtotal)
        val tvAdminFee = view.findViewById<TextView>(R.id.tv_admin_fee)
        val tvTotalPayment = view.findViewById<TextView>(R.id.tv_total_payment)
        val btnPay = view.findViewById<Button>(R.id.btn_pay)

        // Set prices
        tvSubtotal.text = "Rp ${String.format("%,.0f", totalPrice)}"
        tvAdminFee.text = "Rp ${String.format("%,.0f", adminFee)}"

        val totalPayment = totalPrice + adminFee
        tvTotalPayment.text = "Rp ${String.format("%,.0f", totalPayment)}"

        // Setup pay button
        btnPay.setOnClickListener {
            processPayment()
        }
    }

    private fun setupPaymentMethods(view: View) {
        val rgPaymentMethod = view.findViewById<RadioGroup>(R.id.rg_payment_method)

        // Setup click listeners for payment method items
        view.findViewById<View>(R.id.rb_bca).setOnClickListener {
            rgPaymentMethod.check(R.id.rb_bca)
        }

        view.findViewById<View>(R.id.rb_mandiri).setOnClickListener {
            rgPaymentMethod.check(R.id.rb_mandiri)
        }

        view.findViewById<View>(R.id.rb_gopay).setOnClickListener {
            rgPaymentMethod.check(R.id.rb_gopay)
        }

        view.findViewById<View>(R.id.rb_ovo).setOnClickListener {
            rgPaymentMethod.check(R.id.rb_ovo)
        }

        view.findViewById<View>(R.id.rb_dana).setOnClickListener {
            rgPaymentMethod.check(R.id.rb_dana)
        }
    }

    private fun processPayment() {
        val rgPaymentMethod = requireView().findViewById<RadioGroup>(R.id.rg_payment_method)
        val selectedId = rgPaymentMethod.checkedRadioButtonId

        if (selectedId == -1) {
            Toast.makeText(requireContext(), "Pilih metode pembayaran terlebih dahulu", Toast.LENGTH_SHORT).show()
            return
        }

        val paymentMethod = when (selectedId) {
            R.id.rb_bca -> "Bank BCA"
            R.id.rb_mandiri -> "Bank Mandiri"
            R.id.rb_gopay -> "Gopay"
            R.id.rb_ovo -> "OVO"
            R.id.rb_dana -> "DANA"
            else -> "Unknown"
        }

        // Process payment logic here
        Toast.makeText(requireContext(), "Memproses pembayaran dengan $paymentMethod", Toast.LENGTH_SHORT).show()

        // Simulate payment success after 2 seconds
        requireView().postDelayed({
            showPaymentSuccess()
        }, 2000)
    }

    private fun showPaymentSuccess() {
        // Callback to CartActivity to clear cart and finish
        (requireActivity() as? CartActivity)?.onPaymentSuccess()
    }

    companion object {
        fun newInstance(totalPrice: Double): PaymentFragment {
            val fragment = PaymentFragment()
            val args = Bundle()
            args.putDouble("TOTAL_PRICE", totalPrice)
            fragment.arguments = args
            return fragment
        }
    }
}
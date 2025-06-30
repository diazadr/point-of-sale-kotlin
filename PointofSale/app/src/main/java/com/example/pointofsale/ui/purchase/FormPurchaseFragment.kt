package com.example.pointofsale.ui.purchase

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.pointofsale.data.model.Product
import com.example.pointofsale.data.model.Purchase
import com.example.pointofsale.data.model.PurchaseDetail
import com.example.pointofsale.databinding.FragmentFormPurchaseBinding
import com.example.pointofsale.utils.SessionManager
import java.util.*
import java.text.SimpleDateFormat

class FormPurchaseFragment : Fragment() {

    private lateinit var binding: FragmentFormPurchaseBinding
    private lateinit var viewModel: PurchaseViewModel
    private val selectedProducts = mutableListOf<PurchaseDetail>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFormPurchaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[PurchaseViewModel::class.java]

        binding.btnAddProduct.setOnClickListener {
            findNavController().navigate(
                FormPurchaseFragmentDirections.actionFormPurchaseFragmentToProductSelectionFragment()
            )
        }

        binding.btnSavePurchase.setOnClickListener {
            if (validateForm()) {
                savePurchase()
            }
        }

        viewModel.storeSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                Toast.makeText(requireContext(), "Purchase saved successfully", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            } else {
                Toast.makeText(requireContext(), "Failed to save purchase", Toast.LENGTH_SHORT).show()
            }
        }

        findNavController().currentBackStackEntry?.savedStateHandle
            ?.getLiveData<Product>("selected_product")
            ?.observe(viewLifecycleOwner) { product ->
                val detail = PurchaseDetail(
                    productId = product.id,
                    productName = product.name,
                    quantity = 1,
                    unitPrice = product.purchasePrice?.toDouble(),
                    subtotal = product.purchasePrice?.toDouble() ?: 0.0
                )
                addProductToPurchase(detail)
            }
    }

    private fun validateForm(): Boolean {
        var valid = true

        if (selectedProducts.isEmpty()) {
            Toast.makeText(requireContext(), "Please add at least one product", Toast.LENGTH_SHORT).show()
            valid = false
        }

        if (binding.edtSupplierId.text.isNullOrEmpty()) {
            binding.edtSupplierId.error = "Supplier ID is required"
            valid = false
        }

        return valid
    }

    private fun savePurchase() {
        val totalPayment = selectedProducts.sumOf { it.subtotal ?: 0.0 }

        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val formattedDate = dateFormat.format(Date())

        val purchase = Purchase(
            supplierId = binding.edtSupplierId.text.toString().toInt(),
            totalPayment = totalPayment,
            date = formattedDate,
            items = selectedProducts
        )

        val role = SessionManager(requireContext()).fetchUserRole()
        Log.d("PurchaseDebug", "Role saat create purchase: $role")

        viewModel.storePurchase(purchase)
    }


    private fun addProductToPurchase(detail: PurchaseDetail) {
        selectedProducts.add(detail)
        updateProductList()
    }

    private fun updateProductList() {
        val productText = selectedProducts.joinToString("\n") {
            "${it.productName} - ${it.quantity}x (Rp ${it.subtotal})"
        }
        binding.txtSelectedProducts.text = productText
    }
}

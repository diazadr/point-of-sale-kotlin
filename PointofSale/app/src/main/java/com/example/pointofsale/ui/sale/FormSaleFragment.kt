package com.example.pointofsale.ui.sale

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.pointofsale.data.model.Product
import com.example.pointofsale.data.model.Sale
import com.example.pointofsale.data.model.SaleDetail
import com.example.pointofsale.databinding.FragmentFormSaleBinding
import java.util.Date
import java.util.Locale
import java.text.SimpleDateFormat

class FormSaleFragment : Fragment() {

    private lateinit var binding: FragmentFormSaleBinding
    private lateinit var viewModel: SaleViewModel
    private val selectedProducts = mutableListOf<SaleDetail>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFormSaleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[SaleViewModel::class.java]

        binding.btnAddProduct.setOnClickListener {
            findNavController().navigate(
                FormSaleFragmentDirections.actionFormSaleFragmentToProductSelectionFragment()
            )
        }

        binding.btnSaveSale.setOnClickListener {
            if (validateForm()) {
                saveSale()
            }
        }

        viewModel.storeSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                Toast.makeText(requireContext(), "Sale saved successfully", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            } else {
                Toast.makeText(requireContext(), "Failed to save sale", Toast.LENGTH_SHORT).show()
            }
        }

        findNavController().currentBackStackEntry?.savedStateHandle
            ?.getLiveData<Product>("selected_product")
            ?.observe(viewLifecycleOwner) { product ->
                val detail = SaleDetail(
                    productId = product.id,
                    productName = product.name,
                    quantity = 1,
                    unitPrice = product.sellingPrice?.toDouble(),
                    subtotal = product.sellingPrice?.toDouble() ?: 0.0
                )
                addProductToSale(detail)
            }
    }

    private fun validateForm(): Boolean {
        var valid = true

        if (selectedProducts.isEmpty()) {
            Toast.makeText(requireContext(), "Please add at least one product", Toast.LENGTH_SHORT).show()
            valid = false
        }

        if (binding.edtCustomerId.text.isNullOrEmpty()) {
            binding.edtCustomerId.error = "Customer ID is required"
            valid = false
        }

        if (binding.edtPaymentMethod.text.isNullOrEmpty()) {
            binding.edtPaymentMethod.error = "Payment method is required"
            valid = false
        }

        return valid
    }

    private fun saveSale() {
        val totalPrice = selectedProducts.sumOf { it.subtotal ?: 0.0 }

        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val currentDate = dateFormat.format(Date()) // format yang sesuai MySQL

        val sale = Sale(
            customerId = binding.edtCustomerId.text.toString().toInt(),
            paymentMethod = binding.edtPaymentMethod.text.toString(),
            totalPrice = totalPrice,
            transactionDate = currentDate,
            items = selectedProducts
        )

        viewModel.storeSale(sale)
    }

    fun addProductToSale(productDetail: SaleDetail) {
        selectedProducts.add(productDetail)
        updateProductList()
    }

    private fun updateProductList() {
        // Update UI to show selected products
        val productText = selectedProducts.joinToString("\n") {
            "${it.productName} - ${it.quantity}x (Rp ${it.subtotal})"
        }
        binding.txtSelectedProducts.text = productText
    }
}
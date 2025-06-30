package com.example.pointofsale.ui.customer

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.pointofsale.data.model.Customer
import com.example.pointofsale.databinding.FragmentFormCustomerBinding

class FormCustomerFragment : Fragment() {

    private lateinit var binding: FragmentFormCustomerBinding
    private lateinit var viewModel: CustomerViewModel
    private var data: Customer? = null
    private val customerTypes = listOf("Regular", "VIP")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFormCustomerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[CustomerViewModel::class.java]

        // Setup spinner
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, customerTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spnCustomerType.adapter = adapter

        // Cek apakah ada data yang dikirim untuk diedit
        data = FormCustomerFragmentArgs.fromBundle(arguments as Bundle).data
        data?.let { customer ->
            binding.edtCustomerName.setText(customer.name)
            binding.edtCustomerPhone.setText(customer.phone)
            binding.edtCustomerEmail.setText(customer.email)
            binding.edtCustomerAddress.setText(customer.address)

            // Set spinner sesuai data
            val selectedIndex = customerTypes.indexOfFirst { it.equals(customer.customerType, ignoreCase = true) }
            if (selectedIndex >= 0) {
                binding.spnCustomerType.setSelection(selectedIndex)
            }

            binding.btnDelete.visibility = View.VISIBLE
            binding.btnDelete.setOnClickListener {
                deleteData(view)
            }
        }

        binding.btnSave.setOnClickListener {
            if (validateForm()) {
                saveData(view)
            }
        }

        viewModel.storeSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                Toast.makeText(requireContext(), "Customer berhasil disimpan", Toast.LENGTH_SHORT).show()
                navigateBack(view)
            }
        }

        viewModel.updateSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                Toast.makeText(requireContext(), "Customer berhasil diupdate", Toast.LENGTH_SHORT).show()
                navigateBack(view)
            }
        }

        viewModel.deleteSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                Toast.makeText(requireContext(), "Customer berhasil dihapus", Toast.LENGTH_SHORT).show()
                navigateBack(view)
            }
        }
    }

    private fun validateForm(): Boolean {
        var valid = true

        if (binding.edtCustomerName.text.isNullOrEmpty()) {
            binding.edtCustomerName.error = "Nama wajib diisi"
            valid = false
        }

        if (binding.edtCustomerEmail.text.isNullOrEmpty()) {
            binding.edtCustomerEmail.error = "Email wajib diisi"
            valid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.edtCustomerEmail.text.toString()).matches()) {
            binding.edtCustomerEmail.error = "Format email tidak valid"
            valid = false
        }

        return valid
    }

    private fun saveData(view: View) {
        val customer = Customer(
            id = data?.id,
            name = binding.edtCustomerName.text.toString(),
            phone = binding.edtCustomerPhone.text.toString(),
            email = binding.edtCustomerEmail.text.toString(),
            address = binding.edtCustomerAddress.text.toString(),
            customerType = binding.spnCustomerType.selectedItem.toString().lowercase()
        )

        if (data == null) {
            viewModel.storeCustomer(customer)
        } else {
            viewModel.updateCustomer(customer)
        }
    }

    private fun deleteData(view: View) {
        data?.id?.let {
            viewModel.deleteCustomer(it)
        }
    }

    private fun navigateBack(view: View) {
        view.findNavController().navigate(
            FormCustomerFragmentDirections.actionFormCustomerFragmentToCustomerFragment()
        )
    }
}

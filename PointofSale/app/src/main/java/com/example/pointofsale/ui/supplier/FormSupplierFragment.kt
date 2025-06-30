package com.example.pointofsale.ui.supplier

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.pointofsale.data.model.Supplier
import com.example.pointofsale.databinding.FragmentFormSupplierBinding

class FormSupplierFragment : Fragment() {

    private lateinit var binding: FragmentFormSupplierBinding
    private lateinit var viewModel: SupplierViewModel
    private var data: Supplier? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFormSupplierBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[SupplierViewModel::class.java]

        // Get supplier data from arguments
        data = FormSupplierFragmentArgs.fromBundle(arguments as Bundle).data

        data?.let {
            binding.edtSupplierName.setText(it.name)
            binding.edtSupplierPhone.setText(it.phone)
            binding.edtSupplierEmail.setText(it.email)
            binding.edtSupplierAddress.setText(it.address)
            binding.edtSupplierProduct.setText(it.suppliedProduct)

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
                Toast.makeText(requireContext(), "Supplier berhasil disimpan", Toast.LENGTH_SHORT).show()
                navigateBack(view)
            }
        }

        viewModel.updateSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                Toast.makeText(requireContext(), "Supplier berhasil diupdate", Toast.LENGTH_SHORT).show()
                navigateBack(view)
            }
        }

        viewModel.deleteSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                Toast.makeText(requireContext(), "Supplier berhasil dihapus", Toast.LENGTH_SHORT).show()
                navigateBack(view)
            }
        }
    }

    private fun validateForm(): Boolean {
        var valid = true

        if (binding.edtSupplierName.text.isNullOrEmpty()) {
            binding.edtSupplierName.error = "Nama supplier wajib diisi"
            valid = false
        }

        if (binding.edtSupplierEmail.text.isNullOrEmpty()) {
            binding.edtSupplierEmail.error = "Email wajib diisi"
            valid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.edtSupplierEmail.text.toString()).matches()) {
            binding.edtSupplierEmail.error = "Email tidak valid"
            valid = false
        }

        return valid
    }

    private fun saveData(view: View) {
        val supplier = Supplier(
            id = data?.id,
            name = binding.edtSupplierName.text.toString(),
            phone = binding.edtSupplierPhone.text.toString(),
            email = binding.edtSupplierEmail.text.toString(),
            address = binding.edtSupplierAddress.text.toString(),
            suppliedProduct = binding.edtSupplierProduct.text.toString()
        )

        if (data == null) {
            viewModel.storeSupplier(supplier)
        } else {
            viewModel.updateSupplier(supplier)
        }
    }

    private fun deleteData(view: View) {
        data?.id?.let {
            viewModel.deleteSupplier(it)
        }
    }

    private fun navigateBack(view: View) {
        view.findNavController().navigate(
            FormSupplierFragmentDirections.actionFormSupplierFragmentToSupplierFragment()
        )
    }
}
package com.example.pointofsale.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.pointofsale.data.model.Category
import com.example.pointofsale.databinding.FragmentFormCategoryBinding

class FormCategoryFragment : Fragment() {

    private lateinit var binding: FragmentFormCategoryBinding
    private lateinit var viewModel: CategoryViewModel
    private var data: Category? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFormCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[CategoryViewModel::class.java]

        // Ambil data kategori dari argument
        data = FormCategoryFragmentArgs.fromBundle(arguments as Bundle).data

        data?.let {
            binding.edtCategoryName.setText(it.name)
            binding.edtCategoryDescription.setText(it.description)

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
                Toast.makeText(requireContext(), "Kategori berhasil disimpan", Toast.LENGTH_SHORT).show()
                navigateBack(view)
            }
        }

        viewModel.updateSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                Toast.makeText(requireContext(), "Kategori berhasil diupdate", Toast.LENGTH_SHORT).show()
                navigateBack(view)
            }
        }

        viewModel.deleteSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                Toast.makeText(requireContext(), "Kategori berhasil dihapus", Toast.LENGTH_SHORT).show()
                navigateBack(view)
            }
        }

    }

    private fun validateForm(): Boolean {
        var valid = true
        if (binding.edtCategoryName.text.isNullOrEmpty()) {
            binding.edtCategoryName.error = "Nama kategori wajib diisi"
            valid = false
        }
        return valid
    }

    private fun saveData(view: View) {
        val category = Category(
            id = data?.id,
            name = binding.edtCategoryName.text.toString(),
            description = binding.edtCategoryDescription.text.toString()
        )

        if (data == null) {
            viewModel.storeCategory(category)
        } else {
            viewModel.updateCategory(category)
        }
    }

    private fun deleteData(view: View) {
        data?.id?.let {
            viewModel.deleteCategory(it)
        }
    }

    private fun navigateBack(view: View) {
        view.findNavController().navigate(
            FormCategoryFragmentDirections.actionFormCategoryFragmentToCategoryFragment()
        )
    }
}

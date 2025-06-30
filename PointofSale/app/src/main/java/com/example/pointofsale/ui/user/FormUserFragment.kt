package com.example.pointofsale.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.pointofsale.data.model.User
import com.example.pointofsale.databinding.FragmentFormUserBinding

class FormUserFragment : Fragment() {

    private lateinit var binding: FragmentFormUserBinding
    private lateinit var viewModel: UserViewModel
    private var data: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFormUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val roleList = listOf("Admin", "Kasir", "Manager")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, roleList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerRole.adapter = adapter
        // Gunakan ViewModel dari Activity/Parent
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]

        data = FormUserFragmentArgs.fromBundle(arguments as Bundle).data

        data?.let {
            binding.edtUsername.setText(it.username)
            binding.edtPassword.setText(it.password)
            binding.edtPhone.setText(it.phone)

            val rolePosition = roleList.indexOf(it.role)
            if (rolePosition >= 0) {
                binding.spinnerRole.setSelection(rolePosition)
            }

            binding.btnDelete.visibility = View.VISIBLE
            binding.btnDelete.setOnClickListener { deleteData(view) }
        }

        binding.btnSave.setOnClickListener {
            if (validateForm()) saveData(view)
        }

        // Observers
        viewModel.storeSuccess.observe(viewLifecycleOwner) { success ->
            println("Store success observed: $success") // Debug log
            if (success) {
                Toast.makeText(requireContext(), "User berhasil disimpan", Toast.LENGTH_SHORT).show()
                navigateBack(view)
            }
        }


        viewModel.updateSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                Toast.makeText(requireContext(), "User berhasil diupdate", Toast.LENGTH_SHORT).show()
                navigateBack(view)
            }
        }

        viewModel.deleteSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                Toast.makeText(requireContext(), "User berhasil dihapus", Toast.LENGTH_SHORT).show()
                navigateBack(view)
            }
        }

    }

    private fun validateForm(): Boolean {
        var valid = true
        if (binding.edtUsername.text.isNullOrEmpty()) {
            binding.edtUsername.error = "Username wajib diisi"
            valid = false
        }
        if (binding.edtPassword.text.isNullOrEmpty()) {
            binding.edtPassword.error = "Password wajib diisi"
            valid = false
        }
        if (binding.spinnerRole.selectedItem == null) {
            Toast.makeText(requireContext(), "Pilih role user", Toast.LENGTH_SHORT).show()
            valid = false
        }

        return valid
    }

    private fun saveData(view: View) {
        val user = User(
            id = data?.id,
            username = binding.edtUsername.text.toString(),
            password = binding.edtPassword.text.toString(),
            phone = binding.edtPhone.text.toString(),
            role = binding.spinnerRole.selectedItem.toString() // Ambil dari Spinner
        )


        if (data == null) {
            viewModel.storeUser(user)
        } else {
            viewModel.updateUser(user)
        }
    }

    private fun deleteData(view: View) {
        data?.id?.let { viewModel.deleteUser(it) }
    }

    private fun navigateBack(view: View) {
        view.findNavController().navigate(
            FormUserFragmentDirections.actionFormUserFragmentToUserFragment()
        )
    }
}
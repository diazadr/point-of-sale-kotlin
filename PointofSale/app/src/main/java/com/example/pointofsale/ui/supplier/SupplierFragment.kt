package com.example.pointofsale.ui.supplier

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pointofsale.data.model.Supplier
import com.example.pointofsale.databinding.FragmentSupplierBinding

class SupplierFragment : Fragment() {

    private lateinit var binding: FragmentSupplierBinding
    private lateinit var supplierAdapter: SupplierAdapter
    private val viewModel: SupplierViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSupplierBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sessionManager = com.example.pointofsale.utils.SessionManager(requireContext())
        val userRole = sessionManager.fetchUserRole()

        if (userRole == "gudang") {
            binding.btnAddSupplier.visibility = View.GONE
        }

        supplierAdapter = SupplierAdapter(ArrayList())
        supplierAdapter.setOnClickCallBack(object : SupplierAdapter.OnClickCallBack {
            override fun onItemClicked(data: Supplier) {
                editData(view, data)
            }
        })

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvSupplier.layoutManager = layoutManager
        binding.rvSupplier.addItemDecoration(
            DividerItemDecoration(requireContext(), layoutManager.orientation)
        )
        binding.rvSupplier.adapter = supplierAdapter

        observeViewModel()

        viewModel.loadSuppliers()

        binding.btnAddSupplier.setOnClickListener {
            view.findNavController().navigate(
                SupplierFragmentDirections.actionSupplierFragmentToFormSupplierFragment(null)
            )
        }
    }

    private fun observeViewModel() {
        viewModel.suppliers.observe(viewLifecycleOwner, Observer { response ->
            response?.payload?.let {
                supplierAdapter.setData(it)
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { errorMsg ->
            if (errorMsg != null) {
                Log.e("SupplierFragment", "Error: $errorMsg")
            }
        })
    }

    private fun editData(view: View, data: Supplier) {
        view.findNavController().navigate(
            SupplierFragmentDirections.actionSupplierFragmentToFormSupplierFragment(data)
        )
    }
}
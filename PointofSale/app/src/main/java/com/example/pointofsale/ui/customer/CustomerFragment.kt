package com.example.pointofsale.ui.customer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pointofsale.data.model.Customer
import com.example.pointofsale.databinding.FragmentCustomerBinding

class CustomerFragment : Fragment() {

    private lateinit var binding: FragmentCustomerBinding
    private lateinit var customerAdapter: CustomerAdapter
    private val viewModel: CustomerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCustomerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        customerAdapter = CustomerAdapter(ArrayList())
        customerAdapter.setOnClickCallBack(object : CustomerAdapter.OnClickCallBack {
            override fun onItemClicked(data: Customer) {
                editData(view, data)
            }
        })

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvCustomer.layoutManager = layoutManager
        binding.rvCustomer.addItemDecoration(
            DividerItemDecoration(requireContext(), layoutManager.orientation)
        )
        binding.rvCustomer.adapter = customerAdapter

        observeViewModel()

        viewModel.loadCustomers()

        binding.btnAddCustomer.setOnClickListener {
            view.findNavController().navigate(
                CustomerFragmentDirections.actionCustomerFragmentToFormCustomerFragment(null)
            )
        }
    }

    private fun observeViewModel() {
        viewModel.customers.observe(viewLifecycleOwner, Observer { response ->
            response?.payload?.let {
                customerAdapter.setData(it)
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { errorMsg ->
            if (errorMsg != null) {
                Log.e("CustomerFragment", "Error: $errorMsg")
                Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun editData(view: View, data: Customer) {
        view.findNavController().navigate(
            CustomerFragmentDirections.actionCustomerFragmentToFormCustomerFragment(data)
        )
    }
}
package com.example.pointofsale.ui.product

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pointofsale.databinding.FragmentProductBinding
import com.example.pointofsale.data.model.Product
import androidx.lifecycle.Observer

class ProductFragment : Fragment() {
    private lateinit var binding: FragmentProductBinding
    private lateinit var productAdapter: ProductAdapter
    private val viewModel: ProductViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productAdapter = ProductAdapter(ArrayList())
        productAdapter.setOnClickCallBack(object : ProductAdapter.onClickCallBack {
            override fun onItemClicked(data: Product) {
                editData(view, data)
            }
        })

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvProduct.layoutManager = layoutManager
        binding.rvProduct.addItemDecoration(
            DividerItemDecoration(requireContext(), layoutManager.orientation)
        )
        binding.rvProduct.adapter = productAdapter

        observeViewModel()

        viewModel.loadProducts()

        binding.btnAdd.setOnClickListener {
            view.findNavController().navigate(
                ProductFragmentDirections.actionProductFragmentToFormProductFragment(null)
            )
        }
    }

    private fun observeViewModel() {
        viewModel.products.observe(viewLifecycleOwner, Observer { response ->
            response?.payload?.let {
                productAdapter.setData(it)
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { errorMsg ->
            if (errorMsg != null) {
                Log.e("ProductFragment", "Error: $errorMsg")
            }
        })
    }

    private fun editData(view: View, data: Product) {
        view.findNavController().navigate(
            ProductFragmentDirections.actionProductFragmentToFormProductFragment(data)
        )
    }
}

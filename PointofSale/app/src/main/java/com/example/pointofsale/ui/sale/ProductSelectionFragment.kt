package com.example.pointofsale.ui.sale

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pointofsale.data.model.Product
import com.example.pointofsale.databinding.FragmentProductSelectionBinding
import com.example.pointofsale.ui.product.ProductAdapter
import com.example.pointofsale.ui.product.ProductViewModel

class ProductSelectionFragment : Fragment() {

    private lateinit var binding: FragmentProductSelectionBinding
    private val viewModel: ProductViewModel by viewModels()
    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productAdapter = ProductAdapter(ArrayList())
        productAdapter.setOnClickCallBack(object : ProductAdapter.onClickCallBack {
            override fun onItemClicked(data: Product) {
                // Kirim balik data ke fragment sebelumnya jika diperlukan

                findNavController().previousBackStackEntry?.savedStateHandle?.set("selected_product", data)
                findNavController().popBackStack()

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
    }

    private fun observeViewModel() {
        viewModel.products.observe(viewLifecycleOwner, Observer { response ->
            response?.payload?.let {
                productAdapter.setData(it)
            }
        })
    }
}

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
import com.example.pointofsale.data.model.Sale
import com.example.pointofsale.databinding.FragmentSaleBinding

class SaleFragment : Fragment() {

    private lateinit var binding: FragmentSaleBinding
    private lateinit var saleAdapter: SaleAdapter
    private val viewModel: SaleViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSaleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        saleAdapter = SaleAdapter(ArrayList())
        saleAdapter.setOnClickCallBack(object : SaleAdapter.OnClickCallBack {
            override fun onItemClicked(data: Sale) {
                viewModel.loadSaleDetails(data.id.toString())
                findNavController().navigate(
                    SaleFragmentDirections.actionSaleFragmentToSaleDetailFragment(data.id.toString())
                )
            }
        })

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvSales.layoutManager = layoutManager
        binding.rvSales.addItemDecoration(
            DividerItemDecoration(requireContext(), layoutManager.orientation)
        )
        binding.rvSales.adapter = saleAdapter

        observeViewModel()

        viewModel.loadSales()

        binding.btnAddSale.setOnClickListener {
            findNavController().navigate(
                SaleFragmentDirections.actionSaleFragmentToFormSaleFragment()
            )
        }
    }

    private fun observeViewModel() {
        viewModel.sales.observe(viewLifecycleOwner, Observer { response ->
            response?.payload?.let {
                saleAdapter.setData(it)
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { errorMsg ->
            errorMsg?.let {
                // Show error message
            }
        })
    }
}
package com.example.pointofsale.ui.salesreport

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pointofsale.databinding.FragmentSalesReportBinding

class SalesReportFragment : Fragment() {

    private lateinit var binding: FragmentSalesReportBinding
    private lateinit var adapter: SalesReportAdapter
    private val viewModel: SalesReportViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSalesReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SalesReportAdapter(ArrayList())

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvSalesReport.layoutManager = layoutManager
        binding.rvSalesReport.addItemDecoration(
            DividerItemDecoration(requireContext(), layoutManager.orientation)
        )
        binding.rvSalesReport.adapter = adapter

        // AMATI salesByProduct, bukan salesSummary
        viewModel.salesByProduct.observe(viewLifecycleOwner) {
            it.payload?.let { productList -> adapter.setData(productList) }
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Log.e("SalesReportFragment", "Error: $it")
        }

        // FETCH YANG BENAR
        viewModel.fetchSalesByProduct()
    }
}

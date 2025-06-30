package com.example.pointofsale.ui.salesreport

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pointofsale.databinding.FragmentSalesSummaryBinding

class SalesSummaryFragment : Fragment() {

    private lateinit var binding: FragmentSalesSummaryBinding
    private lateinit var adapter: SalesSummaryAdapter
    private val viewModel: SalesReportViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSalesSummaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SalesSummaryAdapter(ArrayList())
        binding.rvSalesSummary.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSalesSummary.addItemDecoration(
            DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        )
        binding.rvSalesSummary.adapter = adapter

        viewModel.salesSummary.observe(viewLifecycleOwner) {
            it.payload?.let { summaryList -> adapter.setData(summaryList) }
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Log.e("SalesSummaryFragment", "Error: $it")
        }

        viewModel.loadSalesSummary()
    }
}

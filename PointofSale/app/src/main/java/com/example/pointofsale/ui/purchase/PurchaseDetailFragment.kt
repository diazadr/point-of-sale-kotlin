package com.example.pointofsale.ui.purchase

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pointofsale.databinding.FragmentPurchaseDetailBinding

class PurchaseDetailFragment : Fragment() {

    private lateinit var binding: FragmentPurchaseDetailBinding
    private lateinit var purchaseDetailAdapter: PurchaseDetailAdapter
    private val viewModel: PurchaseViewModel by viewModels()
    private val args: PurchaseDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPurchaseDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val purchaseId = args.purchaseId
        Log.d("PurchaseDetailFragment", "PurchaseId = $purchaseId")

        purchaseDetailAdapter = PurchaseDetailAdapter(ArrayList())

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvPurchaseDetails.layoutManager = layoutManager
        binding.rvPurchaseDetails.addItemDecoration(
            DividerItemDecoration(requireContext(), layoutManager.orientation)
        )
        binding.rvPurchaseDetails.adapter = purchaseDetailAdapter

        observeViewModel()
        viewModel.loadPurchaseDetails(purchaseId)
    }

    private fun observeViewModel() {
        viewModel.purchaseDetails.observe(viewLifecycleOwner) { response ->
            Log.d("PurchaseDetailFragment", "purchaseDetails response: $response")
            response?.payload?.let {
                Log.d("PurchaseDetailFragment", "payload size: ${it.size}")
                purchaseDetailAdapter.setData(it)

                val total = it.sumOf { detail -> detail.subtotal ?: 0.0 }
                binding.txtTotal.text = "Total: Rp $total"
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMsg ->
            errorMsg?.let {
                Log.e("PurchaseDetailFragment", "Error: $it")
            }
        }

        viewModel.purchases.observe(viewLifecycleOwner) { response ->
            response?.payload?.firstOrNull { it.id.toString() == args.purchaseId }?.let { purchase ->
                binding.txtSupplierName.text = "Supplier: ${purchase.supplierName ?: "-"}"
            }
        }

    }
}

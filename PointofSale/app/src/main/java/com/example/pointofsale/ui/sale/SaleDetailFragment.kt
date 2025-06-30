package com.example.pointofsale.ui.sale

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
import com.example.pointofsale.databinding.FragmentSaleDetailBinding

class SaleDetailFragment : Fragment() {

    private lateinit var binding: FragmentSaleDetailBinding
    private lateinit var saleDetailAdapter: SaleDetailAdapter
    private val viewModel: SaleViewModel by viewModels()
    private val args: SaleDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSaleDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val transactionId = args.transactionId
        viewModel.loadSale(transactionId)

        super.onViewCreated(view, savedInstanceState)
        Log.d("SaleDetailFragment", "TransactionId = $transactionId")

        saleDetailAdapter = SaleDetailAdapter(ArrayList())

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvSaleDetails.layoutManager = layoutManager
        binding.rvSaleDetails.addItemDecoration(
            DividerItemDecoration(requireContext(), layoutManager.orientation)
        )
        binding.rvSaleDetails.adapter = saleDetailAdapter

        observeViewModel()

        viewModel.loadSaleDetails(transactionId)
    }


    private fun observeViewModel() {
        viewModel.saleDetails.observe(viewLifecycleOwner) { response ->
            Log.d("SaleDetailFragment", "saleDetails response: $response")
            response?.payload?.let {
                Log.d("SaleDetailFragment", "saleDetails payload size: ${it.size}")
                saleDetailAdapter.setData(it)

                val total = it.sumOf { detail -> detail.subtotal ?: 0.0 }
                binding.txtTotal.text = "Total: Rp $total"
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMsg ->
            errorMsg?.let {
                Log.e("SaleDetailFragment", "Error: $it")
            }
        }

        viewModel.sale.observe(viewLifecycleOwner) { response ->
            response?.payload?.let { sale ->
                binding.txtTotal.text = "Total: Rp ${sale.totalPrice}"
                binding.txtCustomerName.text = "Customer: ${sale.customerName ?: "-"}"
                binding.txtUserName.text = "Kasir: ${sale.userName ?: "-"}"
                binding.txtPaymentMethod.text = "Metode: ${sale.paymentMethod ?: "-"}"
            }
        }

    }

}
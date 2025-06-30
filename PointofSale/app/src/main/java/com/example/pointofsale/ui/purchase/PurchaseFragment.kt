package com.example.pointofsale.ui.purchase

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
import com.example.pointofsale.data.model.Purchase
import com.example.pointofsale.databinding.FragmentPurchaseBinding

class PurchaseFragment : Fragment() {

    private lateinit var binding: FragmentPurchaseBinding
    private lateinit var purchaseAdapter: PurchaseAdapter
    private val viewModel: PurchaseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPurchaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        purchaseAdapter = PurchaseAdapter(ArrayList())
        purchaseAdapter.setOnClickCallBack(object : PurchaseAdapter.OnClickCallBack {
            override fun onItemClicked(data: Purchase) {
                viewModel.loadPurchaseDetails(data.id.toString())
                findNavController().navigate(
                    PurchaseFragmentDirections.actionPurchaseFragmentToPurchaseDetailFragment(data.id.toString())
                )
            }
        })

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvPurchases.layoutManager = layoutManager
        binding.rvPurchases.addItemDecoration(
            DividerItemDecoration(requireContext(), layoutManager.orientation)
        )
        binding.rvPurchases.adapter = purchaseAdapter

        observeViewModel()
        viewModel.loadPurchases()

        binding.btnAddPurchase.setOnClickListener {
            findNavController().navigate(
                PurchaseFragmentDirections.actionPurchaseFragmentToFormPurchaseFragment()
            )
        }
    }

    private fun observeViewModel() {
        viewModel.purchases.observe(viewLifecycleOwner, Observer { response ->
            response?.payload?.let {
                purchaseAdapter.setData(it)
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { errorMsg ->
            errorMsg?.let {
                // Tampilkan pesan error
            }
        })
    }
}

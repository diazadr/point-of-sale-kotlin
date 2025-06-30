package com.example.pointofsale.ui.category

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
import com.example.pointofsale.data.model.Category
import com.example.pointofsale.databinding.FragmentCategoryBinding

class CategoryFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private val viewModel: CategoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryAdapter = CategoryAdapter(ArrayList())
        categoryAdapter.setOnClickCallBack(object : CategoryAdapter.OnClickCallBack {
            override fun onItemClicked(data: Category) {
                editData(view, data)
            }
        })

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvCategory.layoutManager = layoutManager
        binding.rvCategory.addItemDecoration(
            DividerItemDecoration(requireContext(), layoutManager.orientation)
        )
        binding.rvCategory.adapter = categoryAdapter

        observeViewModel()

        viewModel.loadCategories()

        binding.btnAddCategory.setOnClickListener {
            view.findNavController().navigate(
                CategoryFragmentDirections.actionCategoryFragmentToFormCategoryFragment(null)
            )
        }
    }

    private fun observeViewModel() {
        viewModel.categories.observe(viewLifecycleOwner, Observer { response ->
            response?.payload?.let {
                categoryAdapter.setData(it)
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { errorMsg ->
            if (errorMsg != null) {
                Log.e("CategoryFragment", "Error: $errorMsg")
            }
        })
    }

    private fun editData(view: View, data: Category) {
        view.findNavController().navigate(
            CategoryFragmentDirections.actionCategoryFragmentToFormCategoryFragment(data)
        )
    }
}

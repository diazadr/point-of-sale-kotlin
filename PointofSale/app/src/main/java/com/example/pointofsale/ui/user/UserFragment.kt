package com.example.pointofsale.ui.user

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
import com.example.pointofsale.data.model.User
import com.example.pointofsale.databinding.FragmentUserBinding

class UserFragment : Fragment() {

    private lateinit var binding: FragmentUserBinding
    private lateinit var userAdapter: UserAdapter
    private val viewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userAdapter = UserAdapter(ArrayList())
        userAdapter.setOnClickCallBack(object : UserAdapter.OnClickCallBack {
            override fun onItemClicked(data: User) {
                editData(view, data)
            }
        })

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvUser.layoutManager = layoutManager
        binding.rvUser.addItemDecoration(
            DividerItemDecoration(requireContext(), layoutManager.orientation)
        )
        binding.rvUser.adapter = userAdapter

        observeViewModel()
        viewModel.loadUsers()

        binding.btnAddUser.setOnClickListener {
            view.findNavController().navigate(
                UserFragmentDirections.actionUserFragmentToFormUserFragment(null)
            )
        }
    }

    private fun observeViewModel() {
        viewModel.users.observe(viewLifecycleOwner, Observer { response ->
            response?.payload?.let {
                userAdapter.setData(it)
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { errorMsg ->
            if (errorMsg != null) {
                Log.e("UserFragment", "Error: $errorMsg")
            }
        })
    }

    private fun editData(view: View, data: User) {
        view.findNavController().navigate(
            UserFragmentDirections.actionUserFragmentToFormUserFragment(data)
        )
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadUsers()
    }

}

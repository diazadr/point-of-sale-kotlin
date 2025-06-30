package com.example.pointofsale.ui.dashboard

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.pointofsale.databinding.FragmentDashboardBinding
import com.example.pointofsale.ui.auth.LoginActivity
import com.example.pointofsale.utils.SessionManager

class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sessionManager = SessionManager(requireContext())
        val userRole = sessionManager.fetchUserRole()
        Log.d("DashboardRoleDebug", "Role user saat login: $userRole")

        // Atur teks ucapan selamat datang
        val welcomeText = when (userRole) {
            "admin" -> "Selamat datang, Admin!"
            "gudang" -> "Selamat datang, Petugas Gudang!"
            "kasir" -> "Selamat datang, Kasir!"
            else -> "Selamat datang!"
        }
        binding.tvWelcome.text = welcomeText

        // Atur visibilitas tombol sesuai role
        when (userRole) {
            "admin" -> {
                // Semua tombol tampil
            }

            "gudang" -> {
                binding.btnToUser.visibility = View.GONE
                binding.btnToCategory.visibility = View.GONE
                binding.btnToCustomer.visibility = View.GONE
                binding.btnToSale.visibility = View.GONE
                binding.btnToSalesReportByProduct.visibility = View.GONE
                binding.btnToSalesSummary.visibility = View.GONE
            }

            "kasir" -> {
                binding.btnToUser.visibility = View.GONE
                binding.btnToCategory.visibility = View.GONE
                binding.btnToProduct.visibility = View.GONE
                binding.btnToCustomer.visibility = View.GONE
                binding.btnToSupplier.visibility = View.GONE
                binding.btnToPurchase.visibility = View.GONE
                binding.btnToSalesReportByProduct.visibility = View.GONE
                binding.btnToSalesSummary.visibility = View.GONE
            }

            else -> {
                // Jika role tidak dikenali, sembunyikan semua fitur
                binding.btnToUser.visibility = View.GONE
                binding.btnToCategory.visibility = View.GONE
                binding.btnToProduct.visibility = View.GONE
                binding.btnToCustomer.visibility = View.GONE
                binding.btnToSupplier.visibility = View.GONE
                binding.btnToSale.visibility = View.GONE
                binding.btnToPurchase.visibility = View.GONE
                binding.btnToSalesReportByProduct.visibility = View.GONE
                binding.btnToSalesSummary.visibility = View.GONE
            }
        }

        // Navigasi
        binding.btnToProduct.setOnClickListener {
            view.findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToProductFragment()
            )
        }

        binding.btnToCategory.setOnClickListener {
            view.findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToCategoryFragment()
            )
        }

        binding.btnToUser.setOnClickListener {
            view.findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToUserFragment()
            )
        }

        binding.btnToCustomer.setOnClickListener {
            view.findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToCustomerFragment()
            )
        }

        binding.btnToSupplier.setOnClickListener {
            view.findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToSupplierFragment()
            )
        }

        binding.btnToSale.setOnClickListener {
            view.findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToSaleFragment()
            )
        }

        binding.btnToPurchase.setOnClickListener {
            view.findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToPurchaseFragment()
            )
        }

        binding.btnToSalesReportByProduct.setOnClickListener {
            view.findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToSalesReportFragment()
            )
        }

        binding.btnToSalesSummary.setOnClickListener {
            view.findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToSalesSummaryFragment()
            )
        }

        binding.btnLogout.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Keluar")
                .setMessage("Apakah Anda yakin ingin logout?")
                .setPositiveButton("Ya") { _, _ ->
                    sessionManager.clearSession()
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                .setNegativeButton("Batal", null)
                .show()
        }
    }
}

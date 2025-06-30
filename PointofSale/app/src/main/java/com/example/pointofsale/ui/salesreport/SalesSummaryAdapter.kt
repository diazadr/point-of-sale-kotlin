package com.example.pointofsale.ui.salesreport

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pointofsale.data.model.SalesReportSummary
import com.example.pointofsale.databinding.RowSalesReportSummaryBinding

class SalesSummaryAdapter(private val listData: ArrayList<SalesReportSummary>) :
    RecyclerView.Adapter<SalesSummaryAdapter.DataViewHolder>() {

    fun setData(newData: List<SalesReportSummary>) {
        listData.clear()
        listData.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = RowSalesReportSummaryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val report = listData[position]
        holder.binding.txtReportPeriod.text = "Periode: ${report.reportPeriod}"
        holder.binding.txtTotalRevenue.text = "Total: Rp ${report.totalRevenue}"
        holder.binding.txtTotalTransactions.text = "Transaksi: ${report.totalTransactions}"
        holder.binding.txtBestSellingProduct.text = "Produk Terlaris: ${report.bestSellingProduct}"
    }

    override fun getItemCount(): Int = listData.size

    class DataViewHolder(val binding: RowSalesReportSummaryBinding) :
        RecyclerView.ViewHolder(binding.root)
}

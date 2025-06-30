package com.example.pointofsale.ui.salesreport

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pointofsale.data.model.SalesReportByProduct
import com.example.pointofsale.databinding.RowSalesReportByProductBinding

class SalesReportAdapter(private val listData: ArrayList<SalesReportByProduct>) :
    RecyclerView.Adapter<SalesReportAdapter.DataViewHolder>() {

    fun setData(newData: List<SalesReportByProduct>) {
        listData.clear()
        listData.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = RowSalesReportByProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val report = listData[position]
        holder.binding.txtProductName.text = report.productName
        holder.binding.txtTotalQuantity.text = "Jumlah: ${report.totalQuantity}"
        holder.binding.txtTotalSales.text = "Penjualan: Rp ${report.totalSales}"
    }

    override fun getItemCount(): Int = listData.size

    class DataViewHolder(val binding: RowSalesReportByProductBinding) : RecyclerView.ViewHolder(binding.root)
}

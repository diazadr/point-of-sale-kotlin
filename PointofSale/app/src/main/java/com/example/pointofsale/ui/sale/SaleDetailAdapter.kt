package com.example.pointofsale.ui.sale

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pointofsale.data.model.SaleDetail
import com.example.pointofsale.databinding.RowSaleDetailBinding

class SaleDetailAdapter(private val listData: ArrayList<SaleDetail>) :
    RecyclerView.Adapter<SaleDetailAdapter.DataViewHolder>() {

    fun setData(newData: List<SaleDetail>) {
        listData.clear()
        listData.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = RowSaleDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val detail = listData[position]
        holder.binding.txtProductName.text = detail.productName ?: "Produk #${detail.productId}"

        holder.binding.txtQuantity.text = "${detail.quantity}x"
        holder.binding.txtUnitPrice.text = "Rp ${detail.unitPrice}"
        holder.binding.txtSubtotal.text = "Rp ${detail.subtotal}"
    }

    override fun getItemCount(): Int = listData.size

    class DataViewHolder(val binding: RowSaleDetailBinding) : RecyclerView.ViewHolder(binding.root)
}
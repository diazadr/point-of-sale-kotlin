package com.example.pointofsale.ui.purchase

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pointofsale.data.model.PurchaseDetail
import com.example.pointofsale.databinding.RowPurchaseDetailBinding

class PurchaseDetailAdapter(private val listData: ArrayList<PurchaseDetail>) :
    RecyclerView.Adapter<PurchaseDetailAdapter.DataViewHolder>() {

    fun setData(newData: List<PurchaseDetail>) {
        listData.clear()
        listData.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = RowPurchaseDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val detail = listData[position]

        holder.binding.txtProductId.text = detail.productName ?: "Produk"
        holder.binding.txtQuantity.text = "${detail.quantity}x"
        holder.binding.txtUnitPrice.text = "Rp ${detail.unitPrice}"
        holder.binding.txtSubtotal.text = "Rp ${detail.subtotal}"
    }

    override fun getItemCount(): Int = listData.size

    class DataViewHolder(val binding: RowPurchaseDetailBinding) : RecyclerView.ViewHolder(binding.root)
}

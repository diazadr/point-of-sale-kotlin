package com.example.pointofsale.ui.purchase

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pointofsale.data.model.Purchase
import com.example.pointofsale.databinding.RowPurchaseBinding
import java.text.SimpleDateFormat
import java.util.*

class PurchaseAdapter(private val listData: ArrayList<Purchase>) :
    RecyclerView.Adapter<PurchaseAdapter.DataViewHolder>() {

    private lateinit var onClickCallBack: OnClickCallBack

    fun setData(newData: List<Purchase>) {
        listData.clear()
        listData.addAll(newData)
        notifyDataSetChanged()
    }

    fun setOnClickCallBack(callback: OnClickCallBack) {
        this.onClickCallBack = callback
    }

    interface OnClickCallBack {
        fun onItemClicked(data: Purchase)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = RowPurchaseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val purchase = listData[position]

        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val date = inputFormat.parse(purchase.date ?: "")
        val formattedDate = date?.let { outputFormat.format(it) } ?: ""

        holder.binding.txtPurchaseDate.text = formattedDate
        holder.binding.txtTotalPayment.text = "Rp ${purchase.totalPayment}"
        holder.binding.txtSupplierName.text = "Supplier: ${purchase.supplierName ?: "-"}"


        holder.itemView.setOnClickListener {
            onClickCallBack.onItemClicked(purchase)
        }
    }

    override fun getItemCount(): Int = listData.size

    class DataViewHolder(val binding: RowPurchaseBinding) : RecyclerView.ViewHolder(binding.root)
}

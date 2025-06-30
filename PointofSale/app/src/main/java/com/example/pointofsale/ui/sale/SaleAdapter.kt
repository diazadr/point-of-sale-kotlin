package com.example.pointofsale.ui.sale

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pointofsale.data.model.Sale
import com.example.pointofsale.databinding.RowSaleBinding
import java.text.SimpleDateFormat
import java.util.*

class SaleAdapter(private val listData: ArrayList<Sale>) :
    RecyclerView.Adapter<SaleAdapter.DataViewHolder>() {

    private lateinit var onClickCallBack: OnClickCallBack

    fun setData(newData: List<Sale>) {
        listData.clear()
        listData.addAll(newData)
        notifyDataSetChanged()
    }

    fun setOnClickCallBack(callback: OnClickCallBack) {
        this.onClickCallBack = callback
    }

    interface OnClickCallBack {
        fun onItemClicked(data: Sale)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = RowSaleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val sale = listData[position]

        // Format date
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
        val date = inputFormat.parse(sale.transactionDate ?: "")
        val formattedDate = date?.let { outputFormat.format(it) } ?: ""

        holder.binding.txtTransactionDate.text = formattedDate
        holder.binding.txtTotalPrice.text = "Rp ${sale.totalPrice}"
        holder.binding.txtPaymentMethod.text = sale.paymentMethod ?: "-"

        holder.itemView.setOnClickListener {
            onClickCallBack.onItemClicked(sale)
        }
    }

    override fun getItemCount(): Int = listData.size

    class DataViewHolder(val binding: RowSaleBinding) : RecyclerView.ViewHolder(binding.root)
}
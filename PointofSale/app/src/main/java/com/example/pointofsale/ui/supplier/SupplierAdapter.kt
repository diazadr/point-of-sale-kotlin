package com.example.pointofsale.ui.supplier

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pointofsale.data.model.Supplier
import com.example.pointofsale.databinding.RowSupplierBinding

class SupplierAdapter(private val listData: ArrayList<Supplier>) :
    RecyclerView.Adapter<SupplierAdapter.DataViewHolder>() {

    private lateinit var onClickCallBack: OnClickCallBack

    fun setData(newData: List<Supplier>) {
        listData.clear()
        listData.addAll(newData)
        notifyDataSetChanged()
    }

    fun setOnClickCallBack(callback: OnClickCallBack) {
        this.onClickCallBack = callback
    }

    interface OnClickCallBack {
        fun onItemClicked(data: Supplier)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = RowSupplierBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val supplier = listData[position]
        holder.binding.txtSupplierName.text = supplier.name
        holder.binding.txtSupplierProduct.text = supplier.suppliedProduct
        holder.binding.txtSupplierContact.text = "${supplier.phone} | ${supplier.email}"

        holder.itemView.setOnClickListener {
            onClickCallBack.onItemClicked(supplier)
        }
    }

    override fun getItemCount(): Int = listData.size

    class DataViewHolder(val binding: RowSupplierBinding) : RecyclerView.ViewHolder(binding.root)
}
package com.example.pointofsale.ui.customer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pointofsale.data.model.Customer
import com.example.pointofsale.databinding.RowCustomerBinding

class CustomerAdapter(
    private val listData: ArrayList<Customer> = arrayListOf()
) : RecyclerView.Adapter<CustomerAdapter.DataViewHolder>() {

    private var onClickCallBack: OnClickCallBack? = null

    fun setData(newData: List<Customer>) {
        listData.clear()
        listData.addAll(newData)
        notifyDataSetChanged()
    }

    fun setOnClickCallBack(callback: OnClickCallBack) {
        this.onClickCallBack = callback
    }

    interface OnClickCallBack {
        fun onItemClicked(data: Customer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = RowCustomerBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val customer = listData[position]
        with(holder.binding) {
            txtCustomerName.text = customer.name ?: "-"
            txtCustomerPhone.text = customer.phone ?: "-"
            txtCustomerEmail.text = customer.email ?: "-"
            txtCustomerAddress.text = customer.address ?: "-"
            txtCustomerType.text = customer.customerType?.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase() else it.toString()
            } ?: "-"
        }

        holder.itemView.setOnClickListener {
            onClickCallBack?.onItemClicked(customer)
        }
    }

    override fun getItemCount(): Int = listData.size

    class DataViewHolder(val binding: RowCustomerBinding) :
        RecyclerView.ViewHolder(binding.root)
}

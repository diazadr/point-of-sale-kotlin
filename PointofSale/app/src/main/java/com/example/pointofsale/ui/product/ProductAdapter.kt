package com.example.pointofsale.ui.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pointofsale.databinding.RowProductBinding
import com.example.pointofsale.data.model.Product
import java.text.NumberFormat
import com.example.pointofsale.R // ← ✅ tambahkan ini

class ProductAdapter(private val listData: ArrayList<Product>) : RecyclerView.Adapter<ProductAdapter.DataViewHolder>() {
    private lateinit var OnClickCallBack: onClickCallBack

    fun setData(newData: List<Product>) {
        listData.clear()
        listData.addAll(newData)
        notifyDataSetChanged()
    }

    fun setOnClickCallBack(data: onClickCallBack) {
        this.OnClickCallBack = data
    }

    interface onClickCallBack {
        fun onItemClicked(data: Product)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(RowProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val curProduct = listData[position]
        val formatter = NumberFormat.getCurrencyInstance()

        holder.binding.txtName.text = curProduct.name
        holder.binding.txtItemCode.text = curProduct.itemCode
        holder.binding.txtCategory.text = curProduct.categoryName ?: "-"
        holder.binding.txtStockQuantity.text = curProduct.stockQuantity?.toString() ?: "0"
        holder.binding.txtPurchasePrice.text = formatter.format(curProduct.purchasePrice)
        holder.binding.txtSellingPrice.text = formatter.format(curProduct.sellingPrice)

        // Load image dari URL
        Glide.with(holder.itemView.context)
            .load(curProduct.productImageUrl) // field ini dari response backend
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.binding.imgProduct)

        holder.itemView.setOnClickListener {
            OnClickCallBack.onItemClicked(curProduct)
        }
    }



    override fun getItemCount(): Int = listData.size

    class DataViewHolder(val binding: RowProductBinding) : RecyclerView.ViewHolder(binding.root)
}

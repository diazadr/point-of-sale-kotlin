package com.example.pointofsale.ui.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pointofsale.data.model.Category
import com.example.pointofsale.databinding.RowCategoryBinding

class CategoryAdapter(private val listData: ArrayList<Category>) :
    RecyclerView.Adapter<CategoryAdapter.DataViewHolder>() {

    private lateinit var onClickCallBack: OnClickCallBack

    fun setData(newData: List<Category>) {
        listData.clear()
        listData.addAll(newData)
        notifyDataSetChanged()
    }

    fun setOnClickCallBack(callback: OnClickCallBack) {
        this.onClickCallBack = callback
    }

    interface OnClickCallBack {
        fun onItemClicked(data: Category)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = RowCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val category = listData[position]
        holder.binding.txtCategoryName.text = category.name
        holder.binding.txtCategoryDescription.text = category.description

        holder.itemView.setOnClickListener {
            onClickCallBack.onItemClicked(category)
        }
    }

    override fun getItemCount(): Int = listData.size

    class DataViewHolder(val binding: RowCategoryBinding) : RecyclerView.ViewHolder(binding.root)
}

package com.example.pointofsale.ui.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pointofsale.data.model.User
import com.example.pointofsale.databinding.RowUserBinding

class UserAdapter(private val listData: ArrayList<User>) :
    RecyclerView.Adapter<UserAdapter.DataViewHolder>() {

    private lateinit var onClickCallBack: OnClickCallBack

    fun setData(newData: List<User>) {
        listData.clear()
        listData.addAll(newData)
        notifyDataSetChanged()
    }

    fun setOnClickCallBack(callback: OnClickCallBack) {
        this.onClickCallBack = callback
    }

    interface OnClickCallBack {
        fun onItemClicked(data: User)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = RowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val user = listData[position]
        holder.binding.tvUsername.text = user.username ?: "-"
        holder.binding.tvPhone.text = "Telp: ${user.phone ?: "-"}"
        holder.binding.tvRole.text = "Role: ${user.role ?: "-"}"
        holder.binding.tvPassword.text = "Password: ${user.password ?: "-"}"

        holder.itemView.setOnClickListener {
            onClickCallBack.onItemClicked(user)
        }
    }

    override fun getItemCount(): Int = listData.size

    class DataViewHolder(val binding: RowUserBinding) : RecyclerView.ViewHolder(binding.root)
}

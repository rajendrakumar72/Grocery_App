package com.mrkumar.groceryapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mrkumar.groceryapp.databinding.ItemListBinding
import com.mrkumar.groceryapp.model.UserModel
import com.mrkumar.groceryapp.viewmodel.MainActivityViewModel

class UserAdapter(var list: List<UserModel>, val viewModel: MainActivityViewModel): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    var onItemClick:((UserModel) -> Unit)?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.UserViewHolder {
        val binding=ItemListBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserAdapter.UserViewHolder, position: Int) {
        val currentPos=list[position]
        with(holder){
            with(list[position]){
                binding.tvName.text=this.id
                binding.tvEmail.text=this.body
                binding.tvPhone.text=this.title
            }
        }
        holder.binding.deleteUserID.setOnClickListener {
            viewModel.delete(currentPos)
        }

        holder.binding.parentLayoutClick.setOnClickListener {
            onItemClick?.invoke(currentPos)
        }
    }

    override fun getItemCount(): Int {
        Log.d("TAG", "getItemCount: ${list.size}")
        return list.size
    }

    inner class UserViewHolder( val binding: ItemListBinding):RecyclerView.ViewHolder(binding.root)


}
package com.mrkumar.groceryapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mrkumar.groceryapp.databinding.ItemListBinding
import com.mrkumar.groceryapp.model.UserModel
import com.mrkumar.groceryapp.viewmodel.MainActivityViewModel

class UserAdapter(var list: List<UserModel>, val viewModel: MainActivityViewModel): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.UserViewHolder {
        val binding=ItemListBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserAdapter.UserViewHolder, position: Int) {

        with(holder){
            with(list[position]){
                binding.tvName.text=this.id
                binding.tvEmail.text=this.body
                binding.tvPhone.text=this.title
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class UserViewHolder( val binding: ItemListBinding):RecyclerView.ViewHolder(binding.root)


}
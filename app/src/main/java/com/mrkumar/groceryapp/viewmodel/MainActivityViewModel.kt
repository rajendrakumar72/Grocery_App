package com.mrkumar.groceryapp.viewmodel

import androidx.lifecycle.ViewModel
import com.mrkumar.groceryapp.model.UserModel
import com.mrkumar.groceryapp.repository.UserRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivityViewModel(private val repository: UserRepository):ViewModel() {

    // In coroutines thread insert item in insert function.
    fun insert(item: UserModel) = GlobalScope.launch {
        repository.insert(item)
    }

    // In coroutines thread delete item in delete function.
    fun delete(item: UserModel) = GlobalScope.launch {
        repository.delete(item)
    }

    //Here we initialized allGroceryItems function with repository
    fun allGroceryItems() = repository.allGroceryItems()

}
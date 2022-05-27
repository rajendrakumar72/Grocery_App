package com.mrkumar.groceryapp.repository

import com.mrkumar.groceryapp.model.UserModel
import com.mrkumar.groceryapp.roomDB.UserRoomDB

class UserRepository(private val db: UserRoomDB) {

    suspend fun insert(item: UserModel) = db.getGroceryDao().insert(item)
    suspend fun delete(item: UserModel) = db.getGroceryDao().delete(item)
    suspend fun update(item: UserModel)=db.getGroceryDao().update(item)

    fun allGroceryItems() = db.getGroceryDao().getAllUserData()
}
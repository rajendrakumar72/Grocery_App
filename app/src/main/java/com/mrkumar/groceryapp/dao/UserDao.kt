package com.mrkumar.groceryapp.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mrkumar.groceryapp.model.UserModel

@Dao
interface UserDao {

    // Insert function is used to
    // insert data in database.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: UserModel)

    // Delete function is used to
    // delete data in database.
    @Delete
    suspend fun delete(item: UserModel)

    // getAllUserData function is used to get
    // all the data of database.
    @Query("SELECT * FROM userData")
    fun getAllUserData(): LiveData<List<UserModel>>

    @Update
    suspend fun update(item: UserModel)
}
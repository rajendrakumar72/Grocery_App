package com.mrkumar.groceryapp.roomDB

import android.content.Context
import android.service.autofill.UserData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mrkumar.groceryapp.dao.UserDao
import com.mrkumar.groceryapp.model.UserModel

@Database(entities = [UserModel::class], version = 1)
abstract class UserRoomDB:RoomDatabase(){
    abstract fun getGroceryDao(): UserDao

    companion object {
        @Volatile
        private var instance: UserRoomDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, UserRoomDB::class.java, "UserData.db").build()
    }
}
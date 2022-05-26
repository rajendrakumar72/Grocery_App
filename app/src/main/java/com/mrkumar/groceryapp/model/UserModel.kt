package com.mrkumar.groceryapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userData")
data class UserModel(
    @PrimaryKey(autoGenerate = true) val courseID: Int,
    @ColumnInfo(name = "_id") val id: String?,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "email") val email: String?,
    @ColumnInfo(name = "mobile") val mobile: String?,
    @ColumnInfo(name = "gender") val gender: String?
)

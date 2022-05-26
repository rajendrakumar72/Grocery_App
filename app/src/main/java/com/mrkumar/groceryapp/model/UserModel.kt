package com.mrkumar.groceryapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userData")
data class UserModel(
    @PrimaryKey(autoGenerate = true) val courseID: Int,
    @ColumnInfo(name = "userId") val userId: String?,
    @ColumnInfo(name = "id") val id: String?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "body") val body: String?
//    @ColumnInfo(name = "gender") val gender: String?
)

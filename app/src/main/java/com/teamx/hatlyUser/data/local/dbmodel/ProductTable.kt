package com.teamx.hatlyUser.data.local.dbmodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_table")
class ProductTable(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "word") val product: String

)
package com.teamx.hatlyUser.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.teamx.hatlyUser.data.local.dbmodel.ProductTable

@Dao
interface ProductDao {

    @Query("SELECT * FROM product_table")
    fun getAllProducts(): List<ProductTable>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(product: ProductTable)

    @Query("DELETE FROM product_table")
    suspend fun deleteAllProduct()
}
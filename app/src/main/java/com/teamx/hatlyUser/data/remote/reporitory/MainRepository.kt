package com.teamx.hatlyUser.data.remote.reporitory


import com.teamx.hatlyUser.data.local.db.ProductDao
import com.teamx.hatlyUser.data.remote.ApiService
import javax.inject.Inject


class MainRepository @Inject constructor(
    private val apiService: ApiService,
    var localDataSource: ProductDao,
) {



}
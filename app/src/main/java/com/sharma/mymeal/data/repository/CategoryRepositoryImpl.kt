package com.sharma.mymeal.data.repository

import com.sharma.mymeal.data.model.CategoriesDTO
import com.sharma.mymeal.domain.remote.ApiHelper
import com.sharma.mymeal.domain.repository.CategoriesRepository

class CategoryRepositoryImpl(private val apiHelper: ApiHelper) : CategoriesRepository {
    override suspend fun getCategories(): CategoriesDTO? {
        return apiHelper.getCategories()
    }
}
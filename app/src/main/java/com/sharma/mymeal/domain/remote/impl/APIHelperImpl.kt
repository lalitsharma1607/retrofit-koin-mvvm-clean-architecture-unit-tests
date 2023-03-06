package com.sharma.mymeal.domain.remote.impl

import com.sharma.mymeal.data.model.CategoriesDTO
import com.sharma.mymeal.data.model.MealsDTO
import com.sharma.mymeal.domain.remote.ApiHelper
import com.sharma.mymeal.domain.remote.ApiService

class APIHelperImpl(private val apiService: ApiService) : ApiHelper {
    override suspend fun getCategories(): CategoriesDTO? = apiService.getCategories()

    override suspend fun getMeals(category: String): MealsDTO? = apiService.getMeal(category)
}
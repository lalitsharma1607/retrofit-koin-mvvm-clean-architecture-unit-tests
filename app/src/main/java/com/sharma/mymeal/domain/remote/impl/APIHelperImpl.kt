package com.sharma.mymeal.domain.remote.impl

import com.sharma.mymeal.data.model.CategoriesDTO
import com.sharma.mymeal.data.model.MealsDTO
import com.sharma.mymeal.domain.remote.ApiHelper
import com.sharma.mymeal.domain.remote.ApiService
import retrofit2.Response

class APIHelperImpl(private val apiService: ApiService) : ApiHelper {
    override suspend fun getCategories(): Response<CategoriesDTO?> = apiService.getCategories()

    override suspend fun getMeals(category: String): Response<MealsDTO?> = apiService.getMeal(category)
}
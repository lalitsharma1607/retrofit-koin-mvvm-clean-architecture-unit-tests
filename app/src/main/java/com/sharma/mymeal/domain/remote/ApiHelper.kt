package com.sharma.mymeal.domain.remote


import com.sharma.mymeal.data.model.CategoriesDTO
import com.sharma.mymeal.data.model.MealsDTO
import retrofit2.Response

interface ApiHelper {

    suspend fun getCategories(): Response<CategoriesDTO>
    suspend fun getMeals(category: String): Response<MealsDTO>
}
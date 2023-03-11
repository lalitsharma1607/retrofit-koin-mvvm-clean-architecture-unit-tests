package com.sharma.mymeal.data.repository

import com.sharma.mymeal.data.model.MealsDTO
import com.sharma.mymeal.domain.remote.ApiHelper
import com.sharma.mymeal.domain.repository.MealsRepository
import retrofit2.Response

class MealRepositoryImpl(private val apiHelper: ApiHelper) : MealsRepository {
    override suspend fun getMeals(category: String): Response<MealsDTO?> {
        return apiHelper.getMeals(category)
    }
}
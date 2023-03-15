package com.sharma.mymeal.domain.repository

import com.sharma.mymeal.domain.model.Meal
import com.sharma.mymeal.utils.Result

interface MealsRepository {
    suspend fun getMeals(category: String): Result<List<Meal>>
}
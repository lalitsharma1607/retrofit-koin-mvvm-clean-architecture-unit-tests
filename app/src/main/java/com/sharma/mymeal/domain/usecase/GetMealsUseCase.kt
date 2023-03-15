package com.sharma.mymeal.domain.usecase

import com.sharma.mymeal.domain.model.Meal
import com.sharma.mymeal.domain.repository.MealsRepository
import com.sharma.mymeal.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

open class GetMealUseCase constructor(private val repository: MealsRepository) {

    suspend fun getMeals(category: String): Result<List<Meal>> {
        return repository.getMeals(category)
    }
}
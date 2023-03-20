package com.sharma.mymeal.domain.usecase

import com.sharma.mymeal.domain.model.Meal
import com.sharma.mymeal.domain.repository.MealsRepository
import com.sharma.mymeal.utils.Result

open class GetMealUseCase constructor(private val repository: MealsRepository) {

    suspend operator fun invoke(category: String): Result<List<Meal>> {
        return repository.getMeals(category)
    }
}
package com.sharma.mymeal.domain.repository

import com.sharma.mymeal.data.model.MealsDTO
import com.sharma.mymeal.data.model.toDomainMeal
import com.sharma.mymeal.domain.model.Meal
import retrofit2.Response

interface MealsRepository {
    suspend fun getMeals(category: String): Response<MealsDTO?>

    fun convertToMeals(dto: MealsDTO): List<Meal> {
        return dto.meals?.map { it.toDomainMeal() }.orEmpty()
    }
}
package com.sharma.mymeal.domain.repository

import com.sharma.mymeal.data.model.MealsDTO

interface MealsRepository {
    suspend fun getMeals(category: String): MealsDTO?
}
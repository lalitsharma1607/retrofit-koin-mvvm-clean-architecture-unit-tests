package com.sharma.mymeal.domain.remote


import com.sharma.mymeal.data.model.CategoriesDTO
import com.sharma.mymeal.data.model.MealsDTO

interface ApiHelper {

    suspend fun getCategories(): CategoriesDTO?
    suspend fun getMeals(category: String): MealsDTO?
}
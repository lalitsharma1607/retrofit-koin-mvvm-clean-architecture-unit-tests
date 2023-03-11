package com.sharma.mymeal.domain.repository

import com.sharma.mymeal.data.model.CategoriesDTO
import com.sharma.mymeal.domain.model.Category
import retrofit2.Response

interface CategoriesRepository {
    suspend fun getCategories(): Response<CategoriesDTO?>
    fun convertToCategories(dto: CategoriesDTO): List<Category>
}
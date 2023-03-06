package com.sharma.mymeal.domain.repository

import com.sharma.mymeal.data.model.CategoriesDTO

interface CategoriesRepository {
    suspend fun getCategories(): CategoriesDTO?
}
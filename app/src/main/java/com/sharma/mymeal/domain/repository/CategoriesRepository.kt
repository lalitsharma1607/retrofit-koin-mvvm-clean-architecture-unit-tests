package com.sharma.mymeal.domain.repository

import com.sharma.mymeal.domain.model.Category
import com.sharma.mymeal.utils.Result

interface CategoriesRepository {
    suspend fun getCategories(): Result<List<Category>>
}
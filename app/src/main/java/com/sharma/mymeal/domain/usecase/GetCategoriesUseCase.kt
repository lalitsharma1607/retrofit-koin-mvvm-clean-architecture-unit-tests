package com.sharma.mymeal.domain.usecase

import com.sharma.mymeal.domain.model.Category
import com.sharma.mymeal.domain.repository.CategoriesRepository
import com.sharma.mymeal.utils.Result

open class GetCategoriesUseCase constructor(private val repository: CategoriesRepository) {

    suspend fun getCategories(): Result<List<Category>> {
        return repository.getCategories()
    }
}
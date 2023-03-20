package com.sharma.mymeal.domain.usecase

import com.sharma.mymeal.domain.model.Category
import com.sharma.mymeal.domain.repository.CategoriesRepository
import com.sharma.mymeal.utils.Result

class GetCategoriesUseCase constructor(private val repository: CategoriesRepository) {

    suspend operator fun invoke(): Result<List<Category>> {
        return repository.getCategories()
    }
}
package com.sharma.mymeal.data.repository

import com.sharma.mymeal.data.model.CategoriesDTO
import com.sharma.mymeal.data.model.toDomainCategory
import com.sharma.mymeal.domain.model.Category
import com.sharma.mymeal.domain.remote.ApiHelper
import com.sharma.mymeal.domain.repository.CategoriesRepository
import retrofit2.Response

class CategoryRepositoryImpl(private val apiHelper: ApiHelper) : CategoriesRepository {
    override suspend fun getCategories(): Response<CategoriesDTO?> {
        return apiHelper.getCategories()
    }

    override fun convertToCategories(dto: CategoriesDTO): List<Category> {
        return dto.categories.map { it.toDomainCategory() }
    }
}
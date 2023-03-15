package com.sharma.mymeal.data.mapper

import com.sharma.mymeal.data.model.CategoriesDTO
import com.sharma.mymeal.data.model.toDomainCategory
import com.sharma.mymeal.domain.model.Category

open class CategoryMapper {

    fun convertToCategories(dto: CategoriesDTO?): List<Category> {
        dto?.let {
            it.categories?.run {
                return map { dto -> dto.toDomainCategory() }
            }
        }
        return arrayListOf()
    }
}
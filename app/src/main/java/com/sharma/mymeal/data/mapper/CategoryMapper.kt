package com.sharma.mymeal.data.mapper

import com.sharma.mymeal.data.model.CategoriesDTO
import com.sharma.mymeal.data.model.CategoryDTO
import com.sharma.mymeal.domain.model.Category

class CategoryMapper {

    fun convertToCategories(dto: CategoriesDTO): List<Category> {
        dto.categories?.run {
            return map { dto -> dto.toDomainCategory() }
        }
        return arrayListOf()
    }
}


fun CategoryDTO.toDomainCategory(): Category {
    return with(this) {
        Category(strCategory.orEmpty(), strCategoryThumb.orEmpty())
    }
}
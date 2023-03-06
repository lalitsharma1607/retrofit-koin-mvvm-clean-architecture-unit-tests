package com.sharma.mymeal.data.model

import com.sharma.mymeal.domain.model.Category

data class CategoryDTO(
    val idCategory: String?,
    val strCategory: String?,
    val strCategoryDescription: String?,
    val strCategoryThumb: String?
)

fun CategoryDTO.toDomainCategory(): Category {
    return Category(name = this.strCategory ?: "", thumb = this.strCategoryThumb ?: "")
}
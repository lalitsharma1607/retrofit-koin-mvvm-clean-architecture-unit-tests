package com.sharma.mymeal.presentation.meal_categories

import com.sharma.mymeal.domain.model.Category

data class CategoryListState(
    val isLoading: Boolean = false,
    val data: List<Category>? = null,
    val error: String = ""
)
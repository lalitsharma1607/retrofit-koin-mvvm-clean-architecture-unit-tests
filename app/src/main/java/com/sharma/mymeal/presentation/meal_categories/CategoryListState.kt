package com.sharma.mymeal.presentation.meal_categories

import com.sharma.mymeal.domain.model.Category

sealed class CategoryListState {
    data class Loading(val loading: Boolean): CategoryListState()
    data class Data(val data: List<Category>?) : CategoryListState()
    data class Error(val error: String?): CategoryListState()
}
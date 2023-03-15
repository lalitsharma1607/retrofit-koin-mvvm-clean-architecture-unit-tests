package com.sharma.mymeal.presentation.meal_categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharma.mymeal.domain.usecase.GetCategoriesUseCase
import com.sharma.mymeal.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoriesViewModel constructor(private val categoriesUseCase: GetCategoriesUseCase) :
    ViewModel() {
    private val _categories = MutableStateFlow<CategoryListState>(CategoryListState.Loading(true))
    val categories: StateFlow<CategoryListState> = _categories

    init {
        getCategories()
    }

    fun getCategories() {
        viewModelScope.launch {
            when (val data = categoriesUseCase.getCategories()) {
                is Result.Success -> {
                    _categories.value = CategoryListState.Data(data.data)
                }
                is Result.Error -> {
                    _categories.value = CategoryListState.Error(data.error.orEmpty())
                }
            }
        }
    }
}
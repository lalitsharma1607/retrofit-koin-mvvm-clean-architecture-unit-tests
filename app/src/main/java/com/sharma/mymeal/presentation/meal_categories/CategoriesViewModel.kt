package com.sharma.mymeal.presentation.meal_categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharma.mymeal.domain.usecase.GetCategoriesUseCase
import com.sharma.mymeal.utils.Status
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CategoriesViewModel constructor(val categoriesUseCase: GetCategoriesUseCase) : ViewModel() {
    private val _categories = MutableStateFlow(CategoryListState())
    val categories: StateFlow<CategoryListState> = _categories

    init {
        getCategories()
    }

    fun getCategories() {
        categoriesUseCase().onEach {
            when (it.status) {
                Status.SUCCESS -> _categories.value = CategoryListState(data = it.data)
                Status.ERROR -> _categories.value = CategoryListState(error = it.message.orEmpty())
                Status.LOADING -> _categories.value = CategoryListState(isLoading = true)
            }
        }.launchIn(viewModelScope)
    }
}
package com.sharma.mymeal.presentation.meal_categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharma.mymeal.domain.usecase.GetCategoriesUseCase
import com.sharma.mymeal.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoriesViewModel constructor(private val categoriesUseCase: GetCategoriesUseCase) :
    ViewModel() {
    private val _categories = MutableStateFlow<CategoryListState>(CategoryListState.Loading(true))
    val categories: StateFlow<CategoryListState> = _categories

    init {
        viewModelScope.launch(Dispatchers.Main) {
            getCategories()
        }
    }

    fun getCategories() {
        viewModelScope.launch {
            with(categoriesUseCase.invoke()){
                when (this) {
                    is Result.Success -> {
                        _categories.value = CategoryListState.Data(this.data)
                    }
                    is Result.Error -> {
                        _categories.value = CategoryListState.Error(this.error.orEmpty())
                    }
                }
            }

        }
    }
}
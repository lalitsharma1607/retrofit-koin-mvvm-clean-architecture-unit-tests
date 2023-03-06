package com.sharma.mymeal.koin

import com.sharma.mymeal.domain.usecase.GetCategoriesUseCase
import com.sharma.mymeal.domain.usecase.GetMealUseCase
import com.sharma.mymeal.presentation.meal_categories.CategoriesViewModel
import com.sharma.mymeal.presentation.meal_list.MealsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        GetCategoriesUseCase(get())
        CategoriesViewModel(get())
    }
    viewModel {
        GetMealUseCase(get())
        MealsViewModel(get())
    }
}
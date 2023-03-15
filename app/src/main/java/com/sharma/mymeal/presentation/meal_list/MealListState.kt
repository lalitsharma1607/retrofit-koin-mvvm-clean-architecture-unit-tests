package com.sharma.mymeal.presentation.meal_list

import com.sharma.mymeal.domain.model.Meal

sealed class MealListState {
    data class Loading(val loading: Boolean) : MealListState()
    data class Data(val data: List<Meal>?) : MealListState()
    data class Error(val error: String?) : MealListState()
}
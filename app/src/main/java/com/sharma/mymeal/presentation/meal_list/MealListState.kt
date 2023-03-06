package com.sharma.mymeal.presentation.meal_list

import com.sharma.mymeal.domain.model.Meal

data class MealsState(
    val isLoading: Boolean = false,
    val data: List<Meal>? = null,
    val error: String = ""
) {
}
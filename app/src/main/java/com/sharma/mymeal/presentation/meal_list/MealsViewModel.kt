package com.sharma.mymeal.presentation.meal_list

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.sharma.mymeal.domain.usecase.GetMealUseCase
import com.sharma.mymeal.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MealsViewModel(private val mealsUseCase: GetMealUseCase) : ViewModel() {
    private val _meals = MutableStateFlow<MealListState>(MealListState.Loading(true))
    val meals: StateFlow<MealListState> = _meals

    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(view: ImageView, url: String?) {
            if (!url.isNullOrEmpty()) {
                Glide.with(view.context).load(url).into(view)
            }
        }
    }

    fun getMeals(category: String) {
        viewModelScope.launch {
            when (val data = mealsUseCase.getMeals(category)) {
                is Result.Success -> {
                    _meals.value = MealListState.Data(data.data)
                }
                is Result.Error -> {
                    _meals.value = MealListState.Error(data.error.orEmpty())
                }
            }
        }
    }
}
package com.sharma.mymeal.presentation.meal_list

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.sharma.mymeal.domain.usecase.GetMealUseCase
import com.sharma.mymeal.utils.Status
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MealsViewModel(private val mealsUseCase: GetMealUseCase) : ViewModel() {
    private val _meals = MutableStateFlow(MealsState())
    val meals: StateFlow<MealsState> = _meals

    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(view: ImageView, url: String?) {
            if (!url.isNullOrEmpty()) {
                Glide.with(view.context)
                    .load(url)
                    .into(view)
            }
        }
    }

    fun getMeals(category: String) {
        mealsUseCase(category).onEach {
            when (it.status) {
                Status.SUCCESS -> _meals.value = MealsState(data = it.data)
                Status.ERROR -> _meals.value = MealsState(error = it.message.orEmpty())
                Status.LOADING -> _meals.value = MealsState(isLoading = true)
            }
        }.launchIn(viewModelScope)
    }
}
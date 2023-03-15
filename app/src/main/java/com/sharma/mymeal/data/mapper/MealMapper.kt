package com.sharma.mymeal.data.mapper

import com.sharma.mymeal.data.model.MealsDTO
import com.sharma.mymeal.data.model.toDomainMeal
import com.sharma.mymeal.domain.model.Meal

open class MealMapper {

    fun convertToMeals(dto: MealsDTO?): List<Meal> {
        dto?.let {
            it.meals?.run {
                return map { dto -> dto.toDomainMeal() }
            }
        }
        return arrayListOf()
    }
}
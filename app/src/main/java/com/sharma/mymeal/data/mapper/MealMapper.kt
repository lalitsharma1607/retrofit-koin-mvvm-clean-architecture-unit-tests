package com.sharma.mymeal.data.mapper

import com.sharma.mymeal.data.model.MealDTO
import com.sharma.mymeal.data.model.MealsDTO
import com.sharma.mymeal.domain.model.Meal

class MealMapper {

    fun convertToMeals(dto: MealsDTO): List<Meal> {
        dto.meals?.let {
            return it.map { dto -> dto.toDomainMeal() }
        }
        return arrayListOf()
    }
}

fun MealDTO.toDomainMeal(): Meal {
    return with(this) {
        Meal(idMeal.orEmpty(), strMeal.orEmpty(), strMealThumb.orEmpty())
    }
}

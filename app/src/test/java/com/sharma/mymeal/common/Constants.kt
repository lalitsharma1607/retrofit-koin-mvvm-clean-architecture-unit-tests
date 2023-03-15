package com.sharma.mymeal.common

import com.sharma.mymeal.data.model.CategoriesDTO
import com.sharma.mymeal.data.model.CategoryDTO
import com.sharma.mymeal.data.model.MealDTO
import com.sharma.mymeal.data.model.MealsDTO
import com.sharma.mymeal.domain.model.Category
import com.sharma.mymeal.domain.model.Meal

object Constants {
    val testCategoryDTO = CategoriesDTO(
        arrayListOf(
            CategoryDTO(
                idCategory = "1",
                strCategory = "1",
                strCategoryDescription = null,
                strCategoryThumb = null
            )
        )
    )

    val testMealDTO = MealsDTO(
        arrayListOf(
            MealDTO(
                null,
                "1",
                null,
                "Dessert",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                "Gulab Jamun",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null

            )
        )
    )

    const val FAKE_NAME = "Chicken"
    const val FAKE_ID = "1"
    const val FAKE_THUMB = "http://xyz.com/abc.png"
    val categoryListSizeOne = arrayListOf(Category(FAKE_NAME, FAKE_THUMB))
    val mealListSizeOne = arrayListOf(Meal(FAKE_ID, FAKE_NAME, FAKE_THUMB))
    const val testCategory = "Dessert"

}
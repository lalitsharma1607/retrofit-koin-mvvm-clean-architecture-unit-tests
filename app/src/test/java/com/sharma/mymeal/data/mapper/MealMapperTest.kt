package com.sharma.mymeal.data.mapper

import com.sharma.mymeal.data.model.MealDTO
import com.sharma.mymeal.data.model.MealsDTO
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test

class MealMapperTest {
    private lateinit var mapper: MealMapper

    @Before
    fun setUp() {
        mapper = MealMapper()
    }

    fun getTestDTO(id: String? = "1", category: String? = "Dessert", mealName: String? = "Gulab Jamun"): MealsDTO {
        return MealsDTO(
            arrayListOf(
                MealDTO(
                    null,
                    id,
                    null,
                    category,
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
                    mealName,
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
    }

    @Test
    fun `when convertToCategories is called attributes of CategoryDTO and Category should be same`() {
        val dto = getTestDTO()
        val sut = mapper.convertToMeals(dto)
        assertEquals(
            dto.meals?.firstOrNull()?.strMeal.orEmpty(),
            sut.first().name
        )
        assertEquals(
            dto.meals?.firstOrNull()?.strMealThumb.orEmpty(),
            sut.first().image
        )
    }

    @Test
    fun `when convertToCategories is called size of both lists should be same`() {
        val dto = getTestDTO()
        val sut = mapper.convertToMeals(dto)
        val testMealDTOSize = dto.meals?.size
        assertTrue(sut.size == testMealDTOSize)
    }
}
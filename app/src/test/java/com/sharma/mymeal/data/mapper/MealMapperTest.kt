package com.sharma.mymeal.data.mapper

import com.sharma.mymeal.common.Constants
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

    @Test
    fun `when convertToCategories is called attributes of CategoryDTO and Category should be same`() {
        val sut = mapper.convertToMeals(Constants.testMealDTO)
        assertEquals(
            Constants.testMealDTO.meals?.firstOrNull()?.strMeal.orEmpty(),
            sut.first().name
        )
        assertEquals(
            Constants.testMealDTO.meals?.firstOrNull()?.strMealThumb.orEmpty(),
            sut.first().image
        )
    }

    @Test
    fun `when convertToCategories is called size of both lists should be same`() {
        val sut = mapper.convertToMeals(Constants.testMealDTO)
        val testMealDTOSize = Constants.testMealDTO.meals?.size
        assertTrue(sut.size == testMealDTOSize)
    }
}
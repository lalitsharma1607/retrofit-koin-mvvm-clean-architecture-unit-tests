package com.sharma.mymeal.data.mapper

import com.sharma.mymeal.common.Constants
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test

class CategoryMapperTest {
    private lateinit var mapper: CategoryMapper

    @Before
    fun setUp() {
        mapper = CategoryMapper()
    }

    @Test
    fun `when convertToCategories is called attributes of CategoryDTO and Category should be same`() {
        val sut = mapper.convertToCategories(Constants.testCategoryDTO)
        assertEquals(
            Constants.testCategoryDTO.categories?.firstOrNull()?.strCategory.orEmpty(),
            sut.first().name
        )
        assertEquals(
            Constants.testCategoryDTO.categories?.firstOrNull()?.strCategoryThumb.orEmpty(),
            sut.first().thumb
        )
    }

    @Test
    fun `when convertToCategories is called size of both lists should be same`() {
        val sut = mapper.convertToCategories(Constants.testCategoryDTO)
        val categoryDTOListSize = Constants.testCategoryDTO.categories?.size
        assertTrue(sut.size == categoryDTOListSize)
    }
}
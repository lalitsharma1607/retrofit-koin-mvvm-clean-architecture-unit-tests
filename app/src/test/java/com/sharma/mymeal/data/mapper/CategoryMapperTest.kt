package com.sharma.mymeal.data.mapper

import com.sharma.mymeal.data.model.CategoriesDTO
import com.sharma.mymeal.data.model.CategoryDTO
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test

class CategoryMapperTest {
    private lateinit var mapper: CategoryMapper
    private fun getTestDTO(
        id: String? = "1",
        name: String? = "Chicken",
        desc: String? = null,
        thumb: String? = null
    ): CategoriesDTO {
        return CategoriesDTO(
            arrayListOf(
                CategoryDTO(
                    idCategory = id,
                    strCategory = name,
                    strCategoryDescription = desc,
                    strCategoryThumb = thumb
                )
            )
        )
    }

    @Before
    fun setUp() {
        mapper = CategoryMapper()
    }

    @Test
    fun `when convertToCategories is called attributes of CategoryDTO and Category should be same`() {
        val dto = getTestDTO()
        val sut = mapper.convertToCategories(dto)
        assertEquals(
            dto.categories?.firstOrNull()?.strCategory.orEmpty(),
            sut.first().name
        )
        assertEquals(
            dto.categories?.firstOrNull()?.strCategoryThumb.orEmpty(),
            sut.first().thumb
        )
    }

    @Test
    fun `when convertToCategories is called size of both lists should be same`() {
        val dto = getTestDTO()
        val sut = mapper.convertToCategories(dto)
        val categoryDTOListSize = dto.categories?.size
        assertTrue(sut.size == categoryDTOListSize)
    }
}
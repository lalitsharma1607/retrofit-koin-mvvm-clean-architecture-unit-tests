package com.sharma.mymeal.data.repository

import com.sharma.mymeal.data.model.CategoriesDTO
import com.sharma.mymeal.data.model.CategoryDTO
import com.sharma.mymeal.domain.remote.ApiHelper
import com.sharma.mymeal.domain.repository.CategoriesRepository
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class CategoryRepositoryImplTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = StandardTestDispatcher()

    @Mock
    lateinit var fakeAPIHelper: ApiHelper
    private var repo: CategoriesRepository? = null

    val testDTO = CategoriesDTO(
        arrayListOf(
            CategoryDTO(
                idCategory = "1",
                strCategory = "1",
                strCategoryDescription = null,
                strCategoryThumb = null
            )
        )
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)
        repo = CategoryRepositoryImpl(fakeAPIHelper)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when getCategories is called, it returns the expected value`() = runTest {
        Mockito.`when`(fakeAPIHelper.getCategories()).thenReturn(Response.success(testDTO))
        val sut = repo?.getCategories()
        assertFalse(sut?.body()?.categories.isNullOrEmpty())
        assertTrue(sut?.body()?.categories?.first()?.idCategory == "1")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when getCategories is called, it returns the null`() = runTest {

        Mockito.`when`(fakeAPIHelper.getCategories()).thenReturn(null)
        val sut = repo?.getCategories()
        assertTrue(sut == null)
    }

    @Test
    fun `when convertToCategories is called attributes of CategoryDTO and Category should be same`() {
        val sut = repo?.convertToCategories(testDTO)
        assertEquals(testDTO.categories.first().strCategory.orEmpty(), sut?.first()?.name)
        assertEquals(testDTO.categories.first().strCategoryThumb.orEmpty(), sut?.first()?.thumb)
    }

    @Test
    fun `when convertToCategories is called size of both lists should be same`() {
        val sut = repo?.convertToCategories(testDTO)
        val categoryDTOListSize = testDTO.categories.size
        val convertedListSize = sut?.size ?: 0
        assert(sut != null)
        assertTrue(convertedListSize == categoryDTOListSize)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
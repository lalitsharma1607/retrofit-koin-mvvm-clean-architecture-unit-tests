package com.sharma.mymeal.data.repository

import com.sharma.mymeal.data.model.CategoriesDTO
import com.sharma.mymeal.data.model.CategoryDTO
import com.sharma.mymeal.domain.remote.ApiHelper
import com.sharma.mymeal.domain.repository.CategoriesRepository
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
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

class CategoryRepositoryImplTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = StandardTestDispatcher()

    @Mock
    lateinit var fakeAPIHelper: ApiHelper
    private var repo: CategoriesRepository? = null

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

        val list = CategoriesDTO(
            arrayListOf(
                CategoryDTO(
                    idCategory = "1",
                    strCategory = "1",
                    strCategoryDescription = null,
                    strCategoryThumb = null
                )
            )
        )
        Mockito.`when`(fakeAPIHelper.getCategories()).thenReturn(list)
        val sut = repo?.getCategories()
        assertFalse(sut?.categories.isNullOrEmpty())
        assertTrue(sut?.categories?.first()?.idCategory =="1")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when getCategories is called, it returns the null`() = runTest {

        Mockito.`when`(fakeAPIHelper.getCategories()).thenReturn(null)
        val sut = repo?.getCategories()
        assertTrue(sut == null)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
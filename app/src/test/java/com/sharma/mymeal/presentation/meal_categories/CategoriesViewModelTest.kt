package com.sharma.mymeal.presentation.meal_categories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.sharma.mymeal.data.model.CategoriesDTO
import com.sharma.mymeal.data.model.CategoryDTO
import com.sharma.mymeal.domain.repository.CategoriesRepository
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class CategoriesViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = StandardTestDispatcher()
    private val viewModel: CategoriesViewModel? = null

    @Mock
    val fakeCategoryRepository: CategoriesRepository =
        Mockito.mock(CategoriesRepository::class.java)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when getCategories is called, isLoading is true`() = runTest {
        viewModel?.getCategories()
        viewModel?.categories?.test {
            val emission = awaitItem()
            assertTrue(emission.isLoading)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when getCategories is called, isLoading is false and data is not empty`() = runTest {
        viewModel?.getCategories()
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
        Mockito.`when`(fakeCategoryRepository.getCategories()).thenReturn(list)
        viewModel?.categories?.test {
            val emission2 = awaitItem()
            assertTrue(emission2.data?.size == 1)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
package com.sharma.mymeal.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sharma.mymeal.domain.model.Category
import com.sharma.mymeal.domain.repository.CategoriesRepository
import com.sharma.mymeal.utils.Result
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class GetCategoriesUseCaseTest {

    private val fakeCategory = arrayListOf(Category("Chicken", "http://xyz.com/abc.png"))

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    val fakeCategoryRepository: CategoriesRepository = mock(CategoriesRepository::class.java)

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when getCategories is called then it returns empty result`() = runTest {
        `when`(fakeCategoryRepository.getCategories()).thenReturn(
            Result.Success(arrayListOf())
        )

        val result: Result<List<Category>> =
            GetCategoriesUseCase(fakeCategoryRepository).invoke()
        assertTrue(result is Result.Success)
        val data = (result as Result.Success).data
        assertTrue(data.isEmpty())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when getCategories is called then it returns expected data`() = runTest {
        `when`(fakeCategoryRepository.getCategories()).thenReturn(
            Result.Success(fakeCategory)
        )

        val result: Result<List<Category>> =
            GetCategoriesUseCase(fakeCategoryRepository).invoke()
        assertTrue(result is Result.Success)
        val data = (result as Result.Success).data
        assertTrue(data.size == 1)
    }
}
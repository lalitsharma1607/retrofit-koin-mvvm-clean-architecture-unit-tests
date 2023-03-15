package com.sharma.mymeal.koin

import com.sharma.mymeal.data.repository.CategoryRepositoryImpl
import com.sharma.mymeal.data.repository.MealRepositoryImpl
import com.sharma.mymeal.domain.repository.CategoriesRepository
import com.sharma.mymeal.domain.repository.MealsRepository
import org.koin.dsl.module

val repoModule = module {
    factory<CategoriesRepository> {
        CategoryRepositoryImpl(get(), get())
    }
    factory<MealsRepository> {
        MealRepositoryImpl(get(), get())
    }
}
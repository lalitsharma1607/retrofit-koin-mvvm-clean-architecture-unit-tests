package com.sharma.mymeal.koin

import com.sharma.mymeal.domain.usecase.GetCategoriesUseCase
import com.sharma.mymeal.domain.usecase.GetMealUseCase
import org.koin.dsl.module

val UseCaseDependency = module {
    factory {
        GetCategoriesUseCase(get())
    }

    factory {
        GetMealUseCase(get())
    }
}
package com.greenhelix.mvvm_tutorial_01.utilities

import com.greenhelix.mvvm_tutorial_01.data.FakeDatabase
import com.greenhelix.mvvm_tutorial_01.data.QuoteRepository
import com.greenhelix.mvvm_tutorial_01.ui.quotes.QuoteViewModelFactory

object InjectorUtils {

    fun provideQuotesViewModelFactory(): QuoteViewModelFactory{
        val quoteRepository = QuoteRepository.getInstance(FakeDatabase.getInstance().quoteDao)
        return QuoteViewModelFactory(quoteRepository)
    }
}
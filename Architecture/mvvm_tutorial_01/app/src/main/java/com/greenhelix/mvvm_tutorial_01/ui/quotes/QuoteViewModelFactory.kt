package com.greenhelix.mvvm_tutorial_01.ui.quotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.greenhelix.mvvm_tutorial_01.data.QuoteRepository

class QuoteViewModelFactory(private val quoteRepository: QuoteRepository):ViewModelProvider.NewInstanceFactory() {


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return QuotesViewModel(quoteRepository) as T
    }
}
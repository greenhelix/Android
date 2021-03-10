package com.greenhelix.mvvm_tutorial_01.ui.quotes

import androidx.lifecycle.ViewModel
import com.greenhelix.mvvm_tutorial_01.data.Quote
import com.greenhelix.mvvm_tutorial_01.data.QuoteRepository

class QuotesViewModel(private  val quoteRepository: QuoteRepository) : ViewModel(){
    fun getQuotes() = quoteRepository.getQuotes()
    fun addQuote(quote:Quote) = quoteRepository.addQuote(quote)
}
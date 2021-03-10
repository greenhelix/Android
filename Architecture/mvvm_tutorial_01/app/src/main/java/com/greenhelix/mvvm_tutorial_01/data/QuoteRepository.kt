package com.greenhelix.mvvm_tutorial_01.data

class QuoteRepository private constructor(private  val quoteDao: FakeQuoteDao){

    fun addQuote(quote:Quote){
        quoteDao.addQuote(quote)
    }
    fun getQuotes() = quoteDao.getQuotes()

    companion object {
        @Volatile
        private var instance: QuoteRepository? = null

        fun getInstance(quoteDao: FakeDatabase) = instance ?: synchronized(lock = this){
            instance ?: QuoteRepository(quoteDao).also { instance = it }
        }
    }
}
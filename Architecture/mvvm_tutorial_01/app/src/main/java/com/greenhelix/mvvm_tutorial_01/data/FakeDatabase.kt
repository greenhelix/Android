package com.greenhelix.mvvm_tutorial_01.data

class FakeDatabase private  constructor(){

    var quoteDao = FakeDatabase()
        private set

    companion object {
        @Volatile
        private var instance: FakeDatabase? = null

        fun getInstance() = instance ?: synchronized(lock = this){
            instance ?: FakeDatabase().also { instance = it }
        }
    }
}
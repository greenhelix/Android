package com.greenhelix.dagger

import dagger.Component
import dagger.Module
import dagger.Provides

@Module                                         // 의존성을 제공하는 클래스 - Module사용
public class MyModule {
    @Provides                                   // 의존성을 제공하는 함수  - Provides사용
    fun provideHelloWorld() : String {
        return "Hello World"
    }

}

package com.greenhelix.dagger

import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
//ctrl+shift +f10 을 누르면 실행된다.

class ExampleUnitTest {
    @Test
    fun testHelloWorld(): Unit {  // Unit은 java에서 void와 같다.
        // Mycomponent  에서 getstring함수를 부른것이고 , getstring은 mymodule의 의존성을 제공 받기 때문에, helloworld가 뜨는 것이다.
        val mycomponent: MyComponent = DaggerMyComponent.create()
        println("result▶▶ "+mycomponent.getString())
    }
}
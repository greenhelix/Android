package com.greenhelix.dagger

import dagger.Component
                                            // 해당클래스를 통해 의존성을 제공받는다. 여러개의 클래스도 가능
@Component(modules = [MyModule::class])     // kotlin에서는 []형태로 넣어준다. java -> @Component(modules = MyModule.class)
public interface MyComponent {
    fun getString():String                  //프로비전 메서드, 바인드된 모듈로부터 의존성을 제공한다.
}
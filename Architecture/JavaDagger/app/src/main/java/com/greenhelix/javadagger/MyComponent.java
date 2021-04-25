package com.greenhelix.javadagger;

import androidx.annotation.Nullable;
import dagger.Component;
import dagger.MembersInjector;

/* Dagger - (컴포넌트, 모듈, 객체 등의 관계) == (컨테이너) or (오브젝트 그래프) or (그래프) */


@Component(modules = MyModule.class)
// 조건1. 최소한 하나의 추상적 메서드를 가져야한다.
// 조건2. 메서드의 파라미터와 반환형은 규칙을 엄격히 따라야 한다.
public interface MyComponent {
    /*(1유형) 프로비전 메서드 (파라미터가 없다.) Provision methods */
    String getString();

    // int를 가져오는 모듈 함수에서 nullable을 어노테이션 했으므로 여기도 마찬가지로 해줘야 한다.
    @Nullable
    Integer getInteger();

    /*(2유형) 멤버-인젝션 메서드 (파라미터가 있다.) Member-injection methods */
    // 일반적인 방법
    void inject(MyClass myClass);

    // 멤버-인젝션이지만 파라미터 없이 하는 방법. 단, 반환형의 타입을 선언해주어야 한다.<MyClass>같이
    MembersInjector<MyClass> getInjector();
}

package com.greenhelix.javadagger;

import androidx.annotation.Nullable;
import dagger.Component;
import dagger.MembersInjector;

@Component(modules = MyModule.class)
public interface MyComponent {
    String getString();

    @Nullable  // 이표시를 안하면 null로 리턴이 불가능
    Integer getInteger();

    void inject(MyClass myClass);
    MembersInjector<MyClass> getInjector();
}

// 컴포넌트는 바인딩된 모듈로부터 오브젝트 그래프를 생성하는 핵심적인 역할이다.
// @Component 어노테이션은 Interface 나 abstract추상 클래스에서만 볼 수 있다.

// Component의 속성은 두가지이다.
// 1. modules   :  다른 모듈을 가져와서 써야하기 때문에 대부분은 이걸 쓴다.
// 2. dependencies : 이것은 다른 component를 가져와야 하는 경우(의존)해야하는 경우 쓴다.

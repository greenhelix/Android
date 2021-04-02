package com.greenhelix.javadagger;

import dagger.Component;

@Component(modules = SomeModule.class)
public interface SomeComponent {
    String getSomeType();
}

// component 보통 사용하는 방법이 프로비전 메서드이다. 위의 코드가 프로비전 메서드이다.
// 간단하게, 그냥 다른 모듈을 참조하여 가져온 함수와 그 결과등이 프로비전 메서드 이다. 일반적 방법이다.
// 그냥 매게변수, 인자값이 없는 것을 뜻함.

// 멤버-인젝션 메서드들은 매개변수가 존재한다. 즉 파라미터가 있다.
// void를 반환하는 것이 많다.




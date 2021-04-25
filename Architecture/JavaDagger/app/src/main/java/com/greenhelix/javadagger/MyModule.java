package com.greenhelix.javadagger;

import androidx.annotation.Nullable;
import dagger.Module;
import dagger.Provides;
/* 모듈은 컴포넌트에 의존성을 제공하는 역할 */
@Module
public class MyModule {
    private static final String LOG_TAG = "ik";
    /* Provides는 반환형의 타입을 지켜줘야 한다. 중복되면 에러 발생 */
    @Provides
    String provideHelloWorld(){
        return "Hello World";
    }

    /* Provides는 기본적으로 null반환이 안된다. */
    // nullable을 해주면 가능하다.
    //  그냥하면 이런 오류 뜸. java.lang.NullPointerException: Cannot return null from a non-@Nullable @Provides method
    @Provides
    @Nullable
    Integer provideInteger(){
        return null;
    }
}

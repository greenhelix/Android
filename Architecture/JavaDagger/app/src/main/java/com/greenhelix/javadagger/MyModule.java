package com.greenhelix.javadagger;

import androidx.annotation.Nullable;
import dagger.Module;
import dagger.Provides;

@Module
public class MyModule {
    private static final String LOG_TAG = "ik";
    @Provides
    String provideHelloWorld(){
        return "Hello World";
    }

    @Provides
    @Nullable   // 이표시를 안하면 null로 리턴이 불가능
                //  그냥하면 이런 오류 뜸. java.lang.NullPointerException: Cannot return null from a non-@Nullable @Provides method
    Integer provideInteger(){
        return null;
    }
}

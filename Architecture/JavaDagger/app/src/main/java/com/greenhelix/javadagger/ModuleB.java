package com.greenhelix.javadagger;

import dagger.Module;
import dagger.Provides;

@Module(includes = ModuleA.class)  //ModuleA를 단순히 상속하는 경우 includes를 사용한다.
public class ModuleB {
    @Provides
    String provideB(){
        return "나는 B이다.";
    }
}

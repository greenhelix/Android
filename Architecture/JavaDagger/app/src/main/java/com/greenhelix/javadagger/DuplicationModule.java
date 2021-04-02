package com.greenhelix.javadagger;

import dagger.Module;
import dagger.Provides;

@Module
class DuplicationModule {

    // return값이 동일한 string이므로 에러가 뜬다.
    @Provides
    String provideHelloWorld(){
        return "hello";
    }
    @Provides
    String provideCharles(){
        return "Charles";
    }
}

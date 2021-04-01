package com.greenhelix.javadagger;

import dagger.Module;
import dagger.Provides;

@Module
public class MyModule {
    private static final String LOG_TAG = "ik";
    @Provides
    String provideHelloWorld(){
        return "Hello World";
    }
}

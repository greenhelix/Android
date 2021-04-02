package com.greenhelix.javadagger;

import dagger.Module;
import dagger.Provides;

@Module
public class SomeModule {
    @Provides
    String getSomeType(){
        return "some module type";
    }
}

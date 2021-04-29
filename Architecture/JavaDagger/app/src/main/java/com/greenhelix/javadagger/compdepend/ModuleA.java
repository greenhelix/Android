package com.greenhelix.javadagger.compdepend;

import dagger.Module;
import dagger.Provides;

@Module
public class ModuleA {
    @Provides
    String provideString(){
        return "String from ModuleA";
    }
}

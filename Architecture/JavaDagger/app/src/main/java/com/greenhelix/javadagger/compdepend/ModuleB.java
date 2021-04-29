package com.greenhelix.javadagger.compdepend;

import dagger.Module;
import dagger.Provides;

@Module
public class ModuleB {
    @Provides
    Integer provideInteger(){
        return 100;
    }
}

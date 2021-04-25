package com.greenhelix.javadagger.lazy;

import dagger.Module;
import dagger.Provides;

@Module
public class CounterModule {
    int next = 100;
    @Provides
    Integer provideInteger(){

        System.out.println("Computing....");
        return next++;
    }
}

package com.greenhelix.javadagger.cafe;

import dagger.Module;
import dagger.Provides;

@Module
public class MachineModule {
    @Provides
    CoffeeBean provideCoffeeBean(){
        System.out.println("Machine provided CoffeeBean...");
        return new CoffeeBean();
    }
}

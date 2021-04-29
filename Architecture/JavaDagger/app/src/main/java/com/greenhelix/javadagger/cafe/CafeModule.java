package com.greenhelix.javadagger.cafe;

import dagger.Module;
import dagger.Provides;

@Module(subcomponents = MachineComponent.class)
public class CafeModule {
    @Provides
    Water provideWater(){
        System.out.println("Machine provided Water..");
        return new Water();
    }
    @Provides
    Machine provideMachine(MachineComponent.Builder builder){
        System.out.println("Machine Initiating...");
        return new Machine(builder);
    }
}

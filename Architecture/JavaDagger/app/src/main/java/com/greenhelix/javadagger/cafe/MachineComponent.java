package com.greenhelix.javadagger.cafe;

import dagger.Subcomponent;

@Subcomponent(modules = MachineModule.class)
public interface MachineComponent {
    Coffee getCoffee();
    void inject(Machine machine);

    // CafeModule은 MachineComp의 Builder부분만 의존성을 제공받을 수 있다.
    @Subcomponent.Builder
    interface Builder{
        Builder setMachineModule(MachineModule coffeeMachineModule);
        MachineComponent build();
    }
}

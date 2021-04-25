package com.greenhelix.javadagger.lazy;

import dagger.Component;

@Component(modules = CounterModule.class)
public interface CounterComponent {

    void inject(Counter counter);

}

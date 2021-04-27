package com.greenhelix.javadagger.multibind;

import dagger.Component;

@Component(modules = SetModule.class)
public interface SetComponent {
    void inject(Foo foo);
}

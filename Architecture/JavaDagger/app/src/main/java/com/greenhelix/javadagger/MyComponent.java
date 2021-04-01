package com.greenhelix.javadagger;

import dagger.Component;

@Component(modules = MyModule.class)
public interface MyComponent {
    String getString();
}

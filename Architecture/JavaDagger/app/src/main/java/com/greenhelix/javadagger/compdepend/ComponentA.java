package com.greenhelix.javadagger.compdepend;

import dagger.Component;

@Component(modules = ModuleA.class)
public interface ComponentA {
    String provideString();
}

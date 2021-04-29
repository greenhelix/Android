package com.greenhelix.javadagger.multibind;

import java.util.Set;

import dagger.Component;
import dagger.Subcomponent;

@Subcomponent(modules = ChildModule.class)
public interface ChildComponent {
    Set<String> strings();

    @Subcomponent.Builder
    interface Builder{
        ChildComponent build();
    }
}

package com.greenhelix.javadagger.multibind;

import java.util.Map;

import dagger.Component;

@Component(modules = MapModule.class)
public interface MapComponent {
    Map<String, Long> getLongsByString();
    Map<Class<?>, String> getStringsByClass();
}

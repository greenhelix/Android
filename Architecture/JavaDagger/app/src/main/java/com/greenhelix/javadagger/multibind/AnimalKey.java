package com.greenhelix.javadagger.multibind;

import dagger.MapKey;

@MapKey
public @interface AnimalKey {
    Animal value();
}

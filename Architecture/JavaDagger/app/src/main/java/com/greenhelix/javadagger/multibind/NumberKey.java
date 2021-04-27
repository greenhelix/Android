package com.greenhelix.javadagger.multibind;

import dagger.MapKey;

@MapKey
public @interface NumberKey {
    Class<? extends Number> value();
}

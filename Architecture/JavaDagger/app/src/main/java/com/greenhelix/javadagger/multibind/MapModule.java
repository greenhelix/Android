package com.greenhelix.javadagger.multibind;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;

@Module
public class MapModule {
    @Provides
    @IntoMap
    @StringKey("foo")
    static Long provideFooValue(){
        return 100L;
    }

    @Provides
    @IntoMap
    @ClassKey(Foo.class)
    static String provideFooStr(){
        return "Foo String";
    }

    @IntoMap
    @AnimalKey(Animal.CAT)
    @Provides
    String provideCat(){
        return "Meow";
    }

    @IntoMap
    @AnimalKey(Animal.DOG)
    @Provides
    String provideDog(){
        return "Bow-wow";
    }

    @IntoMap
    @NumberKey(Float.class)
    @Provides
    String provideFloatValue(){
        return "100f";
    }

    @IntoMap
    @NumberKey(Integer.class)
    @Provides
    String provideIntegerValue(){
        return "1";
    }
}

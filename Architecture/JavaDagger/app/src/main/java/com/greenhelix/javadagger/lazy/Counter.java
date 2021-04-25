package com.greenhelix.javadagger.lazy;

import javax.inject.Inject;
import javax.inject.Provider;

import dagger.Lazy;

public class Counter {
    @Inject //Integer로 바인드된 타입을 제너릭으로 갖는 객체를 만들면된다.
    Lazy<Integer> lazy;

    public void printLazy(){
        System.out.println("printing...");
        System.out.println(lazy.get());
        System.out.println(lazy.get());
        System.out.println(lazy.get());
    }

    @Inject
    Provider<Integer> provider;

    public void printProvider(){
        System.out.println("printing...");
        System.out.println(provider.get());
        System.out.println(provider.get());
        System.out.println(provider.get());
    }
}

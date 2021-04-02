package com.greenhelix.javadagger;

import javax.inject.Inject;

public class MyClass {
    @Inject
    String str;

    public String getStr(){
        return str;
    }
}

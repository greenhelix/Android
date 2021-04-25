package com.greenhelix.javadagger;

import javax.inject.Inject;

public class MyClass {
    //의존성을 주입받도록
    @Inject
    String str;

    public String getStr(){
        return str;
    }
}

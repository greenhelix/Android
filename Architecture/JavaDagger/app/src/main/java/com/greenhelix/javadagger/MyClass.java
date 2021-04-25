package com.greenhelix.javadagger;

import javax.inject.Inject;
import javax.inject.Named;

public class MyClass {
    //의존성을 주입받도록
    @Inject
    String str;

    public String getStr(){
        return str;
    }

    @Inject
    @Named("hello")
    String strHello;
    public String getStrHello(){
        return strHello;
    }

    @Inject
    @Named("world")
    String strWorld;
    public String getStrWorld(){
        return strWorld;
    }
}

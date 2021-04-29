package com.greenhelix.javadagger;


import com.greenhelix.javadagger.compdepend.ComponentA;
import com.greenhelix.javadagger.compdepend.ComponentB;
import com.greenhelix.javadagger.compdepend.DaggerComponentA;
import com.greenhelix.javadagger.compdepend.DaggerComponentB;
import com.greenhelix.javadagger.compdepend.Foo;

import org.junit.Test;

public class testDependency {
    @Test
    public void testDependency(){
        Foo foo = new Foo();
        ComponentA componentA = DaggerComponentA.create();
        ComponentB componentB = DaggerComponentB.builder().componentA(componentA).build();

        componentB.inject(foo);
        System.out.println(foo.str);
        System.out.println(foo.integer);
    }
}

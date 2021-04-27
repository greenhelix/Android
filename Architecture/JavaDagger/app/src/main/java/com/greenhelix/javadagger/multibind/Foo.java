package com.greenhelix.javadagger.multibind;

import java.util.Iterator;
import java.util.Set;

import javax.inject.Inject;

public class Foo {
    @Inject
    Set<String> strings;

    public void print(){
        for(Iterator itr = strings.iterator(); itr.hasNext();){
            System.out.println(itr.next());
        }
    }
}

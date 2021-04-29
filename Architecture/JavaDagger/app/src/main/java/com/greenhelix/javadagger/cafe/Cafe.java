package com.greenhelix.javadagger.cafe;

import javax.inject.Inject;

public class Cafe {
    @Inject
    Machine coffeeMachine;

    public Cafe(){
        DaggerCafeComponent.create().inject(this);
    }
    public Coffee orderCoffee(){
        System.out.println("Order Coffee! Coffee Extract Start!");
        return coffeeMachine.extract();
    }
}

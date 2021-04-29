package com.greenhelix.javadagger.cafe;

import javax.inject.Inject;

public class Coffee {
    @Inject
    public Coffee(CoffeeBean coffeBean, Water water){
        System.out.println("Coffee = CoffeeBean + Water!");
    }

}

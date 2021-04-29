package com.greenhelix.javadagger;

import com.greenhelix.javadagger.cafe.Cafe;

import org.junit.Test;

public class CoffeeUnitTest {
    @Test
    public void testCafe(){
        Cafe cafe = new Cafe();
        if (cafe.orderCoffee() != null){
            System.out.println("Complete Coffee!");
        }
    }
}

package com.greenhelix.javadagger;

import org.junit.Test;

import java.net.URI;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void testHelloWorld(){
        MyComponent myComponent = DaggerMyComponent.create();
        System.out.println("result ▶▶▶ "+myComponent.getString());
    }
}
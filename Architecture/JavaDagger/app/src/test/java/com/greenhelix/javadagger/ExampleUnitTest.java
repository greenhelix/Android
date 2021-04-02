package com.greenhelix.javadagger;

import org.junit.Assert;
import org.junit.Test;

import dagger.MembersInjector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


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
        System.out.println("result ▶▶▶ "+myComponent.getInteger());
    }
    @Test
    public void testMemberInjection(){
        MyClass myClass = new MyClass();
        String str = myClass.getStr();
        Assert.assertNull(str);
        MyComponent myComponent = DaggerMyComponent.create();
        myComponent.inject(myClass);
        Assert.assertEquals("Hello World", myClass.getStr());
    }

    @Test
    public void testMemberInjector(){
        MyClass myClass = new MyClass();
        String str = myClass.getStr();
        System.out.println("testMemberInjector::result ▶▶▶ "+ str);

        // 비어 있던 값이, injector를 통해서값이 들어가지고 출려되었다.
        MyComponent myComponent = DaggerMyComponent.create();
        MembersInjector<MyClass> injector = myComponent.getInjector();
        injector.injectMembers(myClass);
        str = myClass.getStr();
        System.out.println("testMemberInjector::result ▶▶▶ " + str);
    }
}
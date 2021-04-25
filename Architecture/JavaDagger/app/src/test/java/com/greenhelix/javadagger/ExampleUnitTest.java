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
        System.out.println("컴포넌트 프로비전 메서드");
        MyComponent myComponent = DaggerMyComponent.create();
        System.out.println("result ▶▶▶ "+myComponent.getString());
        System.out.println("result ▶▶▶ "+myComponent.getInteger());
    }

    //멤버 인젝션이 잘 되는지 확인하려면 아래와 같이 테스트
    // 1번째 방법
    @Test
    public void testMemberInjection(){
        System.out.println("컴포넌트 멤버-인젝션 메서드 방법 1");
        MyClass myClass = new MyClass();
        String str = myClass.getStr();

        // assertNotNull("조회 결과 null", str); 이방법을 하면 에러가 나면서 테스트 실행 안된다.
        System.out.println("testMemberInjection::result ▶▶▶ "+ str); // str은 null 이었다가

        // component를 불러오며 생성한다.
        MyComponent myComponent = DaggerMyComponent.create();
        // inject함수를 호출하여 myClass를 반영해준다.
        myComponent.inject(myClass);

        str = myClass.getStr();
        System.out.println("testMemberInjection::result ▶▶▶ "+ str); // str은 값이 반영되어있다.
    }
    // 2번째 방법
    @Test
    public void testMemberInjector(){
        System.out.println("컴포넌트 멤버-인젝션 메서드 방법 2");
        MyClass myClass = new MyClass();
        String str = myClass.getStr();
        System.out.println("testMemberInjector::result ▶▶▶ "+ str);

        // 컴포넌트를 생성해준다.(위 방법과 동일)
        MyComponent myComponent = DaggerMyComponent.create();
        
        // MembersInjector<T>를 반환하는 방법 - injectMembers(T) 함수를 호출하면 된다. (멤버-인젝션 메서드와 동일한 효과)
        MembersInjector<MyClass> injector = myComponent.getInjector();

        //  위에서는 컴포넌트에서 바로 inject함수를 호출하는 반면 여기서는 injector에서 injectMembers를 호출하여 사용하는 방법이다.
        injector.injectMembers(myClass);

        str = myClass.getStr();
        System.out.println("testMemberInjector::result ▶▶▶ " + str);
    }

    @Test
    public void testInjection(){
        PersomComponent persomComponent = DaggerPersonComponent.create();

        PersonA personA = persomComponent.getPersonA();
        System.out.println(personA.getName() + ":" +personA.getAge());

        PersonB personB = new PersonB();
        DaggerPersonComponent.create().inject(personB);
        assertEquals("Charles", personB.getName());
        assertEquals(100, personB.getAge());
    }
}
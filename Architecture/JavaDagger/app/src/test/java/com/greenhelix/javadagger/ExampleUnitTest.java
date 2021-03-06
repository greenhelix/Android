package com.greenhelix.javadagger;

import com.greenhelix.javadagger.lazy.Counter;
import com.greenhelix.javadagger.lazy.CounterComponent;
import com.greenhelix.javadagger.lazy.DaggerCounterComponent;
import com.greenhelix.javadagger.multibind.Animal;
import com.greenhelix.javadagger.multibind.ChildComponent;
import com.greenhelix.javadagger.multibind.DaggerMapComponent;
import com.greenhelix.javadagger.multibind.DaggerMapKeyComponent;
import com.greenhelix.javadagger.multibind.DaggerParentComponent;
import com.greenhelix.javadagger.multibind.DaggerSetComponent;
import com.greenhelix.javadagger.multibind.Foo;
import com.greenhelix.javadagger.multibind.MapComponent;
import com.greenhelix.javadagger.multibind.MapKeyComponent;
import com.greenhelix.javadagger.multibind.ParentComponent;

import org.junit.Test;

import java.util.Iterator;

import dagger.MembersInjector;

public class ExampleUnitTest {

/*
    컴포넌트의 유형 :
        1. 프로비전 메서드 컴포넌트
        2. 멤버-인젝션 메서드 컴포넌트
                                        */
    @Test //피로비전 메서드 컴포넌트
    public void testHelloWorld(){
        System.out.println("컴포넌트 프로비전 메서드");
        MyComponent myComponent = DaggerMyComponent.create();
        System.out.println("result ▶▶▶ "+myComponent.getString());
        System.out.println("result ▶▶▶ "+myComponent.getInteger());
    }

    // 멤버-인젝션 메서드 컴포넌트
    // 1번째 방법 : 하나의 파라미터를 포함하는 메서드로 컴포넌트 활용
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
    // 2번째 방법 : 반환형을 선언한뒤 파라미터 없이 컴포넌트 활용
    @Test
    public void testMemberInjector(){
        System.out.println("컴포넌트 멤버-인젝션 메서드 방법 2");
        MyClass myClass = new MyClass();
        String str = myClass.getStr();
        System.out.println("testMemberInjector::result ▶▶▶ "+ str);

        /*컴포넌트를 객체화*/
        // 컴포넌트를 생성해준다.(위 방법과 동일)
        MyComponent myComponent = DaggerMyComponent.create();
        
        // MembersInjector<T>를 반환하는 방법 - injectMembers(T) 함수를 호출하면 된다. (멤버-인젝션 메서드와 동일한 효과)
        MembersInjector<MyClass> injector = myComponent.getInjector();

        //  위에서는 컴포넌트에서 바로 inject함수를 호출하는 반면 여기서는 injector에서 injectMembers를 호출하여 사용하는 방법이다.
        injector.injectMembers(myClass);

        str = myClass.getStr();
        System.out.println("testMemberInjector::result ▶▶▶ " + str);
    }

/*
    의존성 주입의 방법은 총 3가지 : 필드 주입, 메서드 주입, 생성자 주입
    실무에서는 주로 필드 주입과 생성자 주입을 사용한다.
                                                                */
    @Test
    public void testInjection(){
        /*컴포넌트를 객체화*/
        // 실행시키면 DaggerPersonComponent가 자동생성되면서 진행됨. 빨간불 뜨면 뭔가 이름이 잘못될 확률이 높다. 알파벳 체크하길!
        PersonComponent personComponent = DaggerPersonComponent.create();

        // 프로비전 컴포넌트 : 바로 컴포넌트에서 getPersonA()를 불러서 생성자에 주입!! personA에 반영해주어서 원할히 사용한다.
        PersonA personA = personComponent.getPersonA();
        System.out.println(personA.getName() + ":" +personA.getAge());

        // 멤버-인젝션 컴포넌트 : 생성자는 따로 선언한뒤, 생성자를 inject에 파라미터로 사용하면 personB의 함수를 원할히 사용가능하다.
        PersonB personB = new PersonB();
        DaggerPersonComponent.create().inject(personB);
        System.out.println(personB.getName() + ":" +personB.getAge());
    }

/*
    Lazy<T> 주입 // Provider<T> 주입
                                                                */
    @Test
    public void testLazy(){
        CounterComponent counterComponent = DaggerCounterComponent.create();
        Counter counter = new Counter();
        System.out.println("===lazy 주입===");
        counterComponent.inject(counter);
        counter.printLazy();

        System.out.println("===provider 주입===");
        counterComponent.inject(counter);
        counter.printProvider();

    }
/*
    한정자 지정 = @Named
                            */
    @Test
    public void testmyComponent(){
        MyClass myClass = new MyClass();
        DaggerMyComponent.create().inject(myClass);
        System.out.println(myClass.getStrHello());
        System.out.println(myClass.getStrWorld());
    }
/*
   MultiBinding : Set같은 객체를 주입하기 위해서는 아래와 같이 한다.
                                                                */
    @Test
    public void testMultibindingSet(){
        Foo foo = new Foo();
        DaggerSetComponent.create().inject(foo);
        foo.print();
    }
/*
   MultiBinding : Map같은 객체를 주입하기 위해서는 아래와 같이 한다.
   Map은 key가 필요하다. @StringKey @ClassKey @IntKey @LongKey
                                                                */
    @Test
    public void testMultibindingMap(){
        MapComponent component = DaggerMapComponent.create();
        long value = component.getLongsByString().get("foo");
        String str = component.getStringsByClass().get(Foo.class);

        System.out.println(value);
        System.out.println(str);

    }
/*
   MultiBinding : 사용자가 직접 @어노테이션을 정의하고 활용할 수 있다.
                                                                */
    @Test
    public void testCustomMapKey(){
        MapKeyComponent component = DaggerMapKeyComponent.create();
        String cat = component.getStringsByAnimal().get(Animal.CAT);
        String dog = component.getStringsByAnimal().get(Animal.DOG);
        String number = component.getStringsByNumber().get(Float.class);

        System.out.println(cat);
        System.out.println(dog);
        System.out.println(number);
    }
/*
   MultiBinding : 상속된 서브 컴포넌트의 멀티 바인딩
   서브 컴포넌트에서 Set이나 Map이 멀티 바인딩 된 상태로 물려받을 수 있다.
   서브 컴포넌트가 부모 컴포넌트로부터 멀티 바인드된 의존성을 그대로 가져온다.
                                                                        */
    @Test
    public void testMultibiningWithSubcomponent(){
        ParentComponent parentComp = DaggerParentComponent.create();
        ChildComponent childComp = parentComp.childCompBuilder().build();

        System.out.println("List set in Parent");

        // Iterator 은 컬렉션에 있는 값들을 읽어오는 함수
        Iterator itr = parentComp.strings().iterator();

        while(itr.hasNext()){
            System.out.println(itr.next());
        }
        System.out.println("List set in Child");

        itr = childComp.strings().iterator();
        // Chile모듈과 Parent모듈에 있는 값을 둘다 가져온다.
        while(itr.hasNext()){
            System.out.println(itr.next());
        }
    }
}
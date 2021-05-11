package com.greenhelix.javadagger.androidDagger;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

//  앱 컴포넌트는 주로 팩토리나 빌더를 통해 생성된다. 여기서는 팩토리를 통해 생성.
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    MainActivityComponent.Builder mainActivityComponentBuilder();

    void inject(App app);

    // 팩토리
    @Component
    interface Factory{
        AppComponent create(
                // create메서드로 앱 모듈과 앱 클래스를 받는다.
                @BindsInstance App app, AppModule appModule
        );
    }
}

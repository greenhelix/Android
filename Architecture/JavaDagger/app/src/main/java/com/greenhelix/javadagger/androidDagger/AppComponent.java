package com.greenhelix.javadagger.androidDagger;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Component(modules = AppModule.class)
@Singleton
public interface AppComponent {
    MainActivityComponent.Builder mainActivityComponentBuilder();
    void inject(App app);

    @Component
    interface Factory{
        AppComponent create(
                @BindsInstance App app,
                AppModule appModule
        );
    }
}

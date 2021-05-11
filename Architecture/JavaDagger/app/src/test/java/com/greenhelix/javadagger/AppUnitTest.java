package com.greenhelix.javadagger;

import com.greenhelix.javadagger.androidDagger.App;
import com.greenhelix.javadagger.androidDagger.AppComponent;

import org.junit.Test;

public class AppUnitTest {
    @Test
    public void appTest(){
        App app = new App();
        AppComponent appComponent = DaggerAppComponent.create();

    }
}

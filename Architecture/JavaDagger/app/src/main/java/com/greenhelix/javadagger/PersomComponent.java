package com.greenhelix.javadagger;

import dagger.Component;

@Component(modules = PersonModule.class)
public interface PersomComponent {
    /*Provision methods*/
    PersonA getPersonA();

    /*Member-Injection methods*/
    void inject(PersonB personB);
}

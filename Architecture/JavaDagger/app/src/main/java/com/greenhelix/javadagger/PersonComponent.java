package com.greenhelix.javadagger;

import dagger.Component;

@Component(modules = PersonModule.class)
public interface PersonComponent {

    /*Provision methods*/
    PersonA getPersonA();

    /*Member-Injection methods*/
    void inject(PersonB personB);

}

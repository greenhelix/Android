package com.greenhelix.javadagger.compdepend;

import dagger.Component;

// componentA를 상속하기 위해서는 Dependencies으로 나타내 주면된다.
@Component(modules = ModuleB.class, dependencies = ComponentA.class)
public interface ComponentB {
    void inject(Foo foo);
}

package com.greenhelix.javadagger;

import dagger.Module;
import dagger.Provides;

@Module
public class ModuleA {
    @Provides
    String provideA(){
        return "나는 A에서 왔다.";
    }
}

// 모듈 간 상속을 할 때, 중복되는 타입이 존재하면 안된다.
// 이 점을 주의해서 모듈을 설계하면, 보일러 플레이트 코드를 많이 제거할 수 있을 것이다.

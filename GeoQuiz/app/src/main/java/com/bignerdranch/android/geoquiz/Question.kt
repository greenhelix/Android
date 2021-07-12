package com.bignerdranch.android.geoquiz

import androidx.annotation.StringRes

// @StringRes 애노테이션 지정 이유
// 1. 새성자에게 유효한 문자열 리소스 ID를 제공하는지를 컴파일 시점에서 Linit가 검사한다.
// 즉, 유효하지 않은 리소스 ID가 생성자에 사용되어 런타임 시에 앱이 중단되는 것을 방지해준다.
// 2. 애노테이션을 지정함으로써 다른 개발자가 쉽게 코드를 알 수 있는 효과가 있다.
// Int형으로 타입을 지정한 이유는 문자열 리소스의 리소스 ID는 항상 int타입을 갖기 때문이다.

data class Question(@StringRes val textResId: Int, val answer: Boolean)

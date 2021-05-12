/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.hilt.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android.hilt.LogApplication
import com.example.android.hilt.R
import com.example.android.hilt.navigator.AppNavigator
import com.example.android.hilt.navigator.Screens
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Main activity of the application.
 *
 * Container for the Buttons & Logs fragments. This activity simply tracks clicks on buttons.
 */
// 앱을 실행하기 위해서 Fragment를 호스팅하는 Activity를 알아야한다.
// 그것이 MainActivity이고 여기에 Android Entry Point로 지정을 해준다.
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // AppNavigator는 Interface이라서 생성자 삽입을 사용할 수 없다.
    // 인터페이스에 사용할 구현을 Hilt에 알리려면 hilt모듈 내 함수에
    // @Binds주석을 사용한다. 이 주석은 항상 추상 함수에 달아야한다.

    // 추상 클래스도 결합이 되었으므로 이제 inject주석을 통해 가져온다.
    @Inject lateinit var navigator: AppNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // navigator 초기화 코드를 삭제한다.
        //navigator = (applicationContext as LogApplication).serviceLocator.provideNavigator(this)

        if (savedInstanceState == null) {
            navigator.navigateTo(Screens.BUTTONS)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        if (supportFragmentManager.backStackEntryCount == 0) {
            finish()
        }
    }
}

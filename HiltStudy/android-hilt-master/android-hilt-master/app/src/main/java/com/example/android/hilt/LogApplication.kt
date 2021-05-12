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

package com.example.android.hilt

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// serviceLocator 인스턴스를 앱의 수명주기에 연결된 컨테이너 추가하려면 아래의 어노테이션을 붙인다.
@HiltAndroidApp
class LogApplication : Application() {
    // 인스턴스 생성
    lateinit var serviceLocator: ServiceLocator

    override fun onCreate() {
        super.onCreate()
        // serviceLocator는 앱의 수명주기에 연결되어 종속 항목의 컨테이너이다.
        // 컨테이너는 코드베이스에 종속 항목을 제공하는 클래스(=module)
        serviceLocator = ServiceLocator(applicationContext)
    }
}

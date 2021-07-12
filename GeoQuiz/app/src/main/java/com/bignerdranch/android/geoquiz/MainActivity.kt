package com.bignerdranch.android.geoquiz

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // lateinit을 넣은 이유 :
    // 1. 코틀린에서는 클래스 속성을 정의할 때 초기화하지 않으면 에러가 되므로 지정했다.
    // 이렇게 하면 두 속성을 사용하기 전에 우리가 책임지고 초기화하겠다는 것을 컴파일러에 알려준다.
    // 2. 두 속성이 컴파일 시점에서 초기화 될 수 없기 때문이다.
    private lateinit var trueButton : Button
    private lateinit var falseButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)

        trueButton.setOnClickListener { view: View ->
            Toast.makeText(this,
            R.string.correct_toast,
            Toast.LENGTH_SHORT).show()
        }
        falseButton.setOnClickListener { view:View ->
            Toast.makeText(this,
                R.string.incorrect_toast,
                Toast.LENGTH_SHORT).show()
        }
    }
}

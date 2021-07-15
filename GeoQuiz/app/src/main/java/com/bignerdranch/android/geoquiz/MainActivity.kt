package com.bignerdranch.android.geoquiz

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

// 클래스 밖에 선언한 변수를 "최상위 수준 속성"이라고 한다.
// 1. 특정 클래스의 인스턴스를 생성하지 않고 바로 사용할 수 있으므로 app실행동안 속성값을 계속 보존해야 할 때 사용한다.
// 2. app 전체에서 사용하는 상수를 정의할 때 사용한다. 여기서는 상수로 활용한다.

private const val IK = "main"

class MainActivity : AppCompatActivity() {

    // lateinit을 넣은 이유 :
    // 1. 코틀린에서는 클래스 속성을 정의할 때 초기화하지 않으면 에러가 되므로 지정했다.
    // 이렇게 하면 두 속성을 사용하기 전에 우리가 책임지고 초기화하겠다는 것을 컴파일러에 알려준다.
    // 2. 두 속성이 컴파일 시점에서 초기화 될 수 없기 때문이다.
    private lateinit var trueButton : Button
    private lateinit var falseButton : Button
    private lateinit var nextButton : ImageButton
    private lateinit var previousButton : ImageButton
    private lateinit var questionTextView : TextView
    private lateinit var checker : MutableList<Int>

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa,false),
        Question(R.string.question_americas,true),
        Question(R.string.question_asia, true)
    )
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(IK, "onCreate called")
        checker = mutableListOf(1,1,1,1,1,1)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_text_view)
        previousButton = findViewById(R.id.previous_button)

        trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
        }
        falseButton.setOnClickListener { view:View ->
            checkAnswer(false)
        }

        nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        previousButton.setOnClickListener { view:View ->
            currentIndex = if(currentIndex != 0){
                (currentIndex - 1) % questionBank.size
            } else{
                questionBank.size-1
            }
            updateQuestion()
        }

        questionTextView.setOnClickListener { view:View ->
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        updateQuestion()
    } // oncreate

    override fun onDestroy() {
        super.onDestroy()
        Log.d(IK, "onDestroy() called")
    }

    override fun onStart() {
        super.onStart()
        Log.d(IK, "onStart() called")
    }
    override fun onStop() {
        super.onStop()
        Log.d(IK, "onStop() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(IK, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(IK, "onPause() called")
    }

    private fun updateQuestion(){
        if(checker[currentIndex]==0){
            trueButton.visibility = View.GONE
            falseButton.visibility = View.GONE
        }else{
            trueButton.visibility = View.VISIBLE
            falseButton.visibility = View.VISIBLE
        }
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer = questionBank[currentIndex].answer
        val messageResId = if (userAnswer == correctAnswer){
            R.string.correct_toast
        }else{
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
        if(messageResId == R.string.correct_toast){
            checker[currentIndex] = 0
        }
    }
}

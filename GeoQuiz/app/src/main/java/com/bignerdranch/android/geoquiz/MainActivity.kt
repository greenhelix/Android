package com.bignerdranch.android.geoquiz

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

// 클래스 밖에 선언한 변수를 "최상위 수준 속성"이라고 한다.
// 1. 특정 클래스의 인스턴스를 생성하지 않고 바로 사용할 수 있으므로 app실행동안 속성값을 계속 보존해야 할 때 사용한다.
// 2. app 전체에서 사용하는 상수를 정의할 때 사용한다. 여기서는 상수로 활용한다.

private const val IK = "main"
private const val KEY_INDEX = "index"

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


    // by lazy를 사용하면, quizViewModel을 val로 지정할 수 있다.
    // 왜냐하면, 활동 인스턴스가 생성될 때, quizViewModel인스턴스 참조를 quizViewModel에 한번만 저장한다.
    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this).get(QuizViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0)?: 0
        quizViewModel.currentIndex = currentIndex
        // 뷰모델을 oncreate에서 생성해도 되지만, 독맂벅으로 선언후 사용하는게 더 났다.
        // 현재 활동과 연관된 뷰모델제공자 인스턴스를 생성하고 반환한다.
//        val provider: ViewModelProvider = ViewModelProvider(this)
        // quiz뷰모델 인스턴스를 반환한다.
//        val quizViewModel = provider.get(QuizViewModel::class.java)
//      이렇게 한줄로 작성가능하다.
//      ViewModelProvider(this).get(QuizViewModel::class.java)
//        Log.d(IK, "Got a QuizViewModel: $quizViewModel")

        Log.d(IK, "onCreate called")

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
            quizViewModel.moveToNext()
            updateQuestion()
        }

        previousButton.setOnClickListener { view:View ->
            quizViewModel.moveToBefore()
            updateQuestion()
        }

        questionTextView.setOnClickListener { view:View ->
            quizViewModel.moveToNext()
            updateQuestion()
        }
        updateQuestion()
    } // oncreate

    private fun updateQuestion(){
        if (quizViewModel.checker()){
            trueButton.visibility = View.GONE
            falseButton.visibility = View.GONE
        }
        else{
            trueButton.visibility = View.VISIBLE
            falseButton.visibility = View.VISIBLE
        }
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = if (userAnswer == correctAnswer){
            R.string.correct_toast
        }else{
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
        if(messageResId == R.string.correct_toast){
            quizViewModel.checker[quizViewModel.currentIndex]= 0
        }
    }

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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(IK, "onSaveInstanceState")
        outState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }
}//END

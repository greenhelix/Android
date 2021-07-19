package com.bignerdranch.android.geoquiz

import android.app.Activity
import android.content.Intent
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
private const val REQUEST_CODE_CHEAT = 0

class MainActivity : AppCompatActivity() {

    // lateinit을 넣은 이유 :
    // 1. 코틀린에서는 클래스 속성을 정의할 때 초기화하지 않으면 에러가 되므로 지정했다.
    // 이렇게 하면 두 속성을 사용하기 전에 우리가 책임지고 초기화하겠다는 것을 컴파일러에 알려준다.
    // 2. 두 속성이 컴파일 시점에서 초기화 될 수 없기 때문이다.
    private lateinit var trueButton : Button
    private lateinit var falseButton : Button
    private lateinit var cheatButton : Button
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

//        savedInstanceState에 값이 있다면, 키와 그 값을 0 지정하고, 없다면, 그냥 0으로 지정한다.
//        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0)?: 0
//        val currentIndex = quizViewModel.currentIndex

        Log.d(IK, "onCreate called")

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        cheatButton = findViewById(R.id.cheat_button)
        questionTextView = findViewById(R.id.question_text_view)
        previousButton = findViewById(R.id.previous_button)

        trueButton.setOnClickListener {
            checkAnswer(true)
        }
        falseButton.setOnClickListener {
            checkAnswer(false)
        }

        cheatButton.setOnClickListener {
            // Cheatactivity에 동반객체로 함수로 만들었기 때문에 간편하게 사용이 가능하다.
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            startActivityForResult(intent, REQUEST_CODE_CHEAT)

            quizViewModel.cheatCheck(quizViewModel.currentIndex)
        }

        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }

        previousButton.setOnClickListener {
            quizViewModel.moveToBefore()
            updateQuestion()
        }

        questionTextView.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }
        updateQuestion()
    } // oncreate

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode != Activity.RESULT_OK){
            quizViewModel.isCheater = true
            return
        }

        if(requestCode== REQUEST_CODE_CHEAT ){
            quizViewModel.isCheater = data?.getBooleanExtra(EXTRA_ANSWER_IS_SHOWN, true)?: false
            if (quizViewModel.isCheater){
                Log.d(IK, "now isCheater? ${quizViewModel.isCheater}, ${quizViewModel.cheatChecker}")
                quizViewModel.cheatCheck(quizViewModel.currentIndex)
            }

        }
    }

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
        val messageResId = when{
            quizViewModel.cheatChecker[quizViewModel.currentIndex] == 0 -> R.string.judgment_toast
            userAnswer == correctAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast
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
        // Bundle인 outState에  key, value 형태로 저장
        outState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }
}//END

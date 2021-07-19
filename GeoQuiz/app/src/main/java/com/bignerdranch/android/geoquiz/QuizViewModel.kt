package com.bignerdranch.android.geoquiz

import android.util.Log
import androidx.lifecycle.ViewModel

private const val IK = "QuizViewModel"

class QuizViewModel :ViewModel(){
    // 외부 클래스에서 이 currentIndex를 사용할 수 있도록 private를 지워준다.
    var currentIndex = 0
    var isCheater = false
    var checker : MutableList<Int> = mutableListOf(1,1,1,1,1,1)
    var cheatChecker : MutableList<Int> = mutableListOf(1,1,1,1,1,1)

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa,false),
        Question(R.string.question_americas,true),
        Question(R.string.question_asia, true)
    )

    val currentQuestionAnswer : Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    fun checker(): Boolean {
        if(checker[currentIndex]==0){
            return true
        }
        return false
    }
    fun cheatCheck(index : Int) : Boolean{
        cheatChecker[index] = 0
        Log.d(IK, "now Cheat $cheatChecker, $currentIndex")
        return true
    }

    fun moveToNext(){
        currentIndex = (currentIndex + 1) % questionBank.size
    }
    fun moveToBefore(){
        currentIndex = if(currentIndex != 0){
            (currentIndex - 1) % questionBank.size
        } else{
            questionBank.size-1
        }
    }


}
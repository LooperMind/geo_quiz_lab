package com.bignerdranch.android.geomain

import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"

class QuizViewModel : ViewModel() {

    var questionsAnswered : Int = 0
    var questionsAnsweredCorrectly : Int = 0

    var currentIndex = 0

    private val questionBank = listOf(
        Question(R.string.question_australia, true, false),
        Question(R.string.question_oceans, true, false),
        Question(R.string.question_mideast, false, false),
        Question(R.string.question_africa, false, false),
        Question(R.string.question_americas, true, false),
        Question(R.string.question_asia, true, false)
    )

    val questionBankSize: Int get() = questionBank.size
    val currentQuestionAnswer: Boolean
        get() =
            questionBank[currentIndex].answer
    val currentQuestionText: Int
        get() =
            questionBank[currentIndex].textResId
    val currentQuestionIsAnswered: Boolean
        get() =
            questionBank[currentIndex].answered
    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }
    fun moveToPrev(){
        currentIndex = (currentIndex-1+questionBank.size)%questionBank.size
    }

    fun MakeCurrentQuestionAnswered(){
        questionsAnswered++
        questionBank[currentIndex].answered = true
    }
    fun ResetQuestions(){
        for (question in questionBank){
            question.answered = false
        }
    }
}
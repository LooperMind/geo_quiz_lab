package com.bignerdranch.android.geomain

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton : Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var prevButton: Button
    private lateinit var questionTextView: TextView

    private val quizViewModel: QuizViewModel by lazy<QuizViewModel> {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "OnCreate called")
        setContentView(R.layout.activity_main)
        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)
        questionTextView = findViewById(R.id.question_text_view)
        trueButton.setOnClickListener {
            checkAnswer(true)
            UpdateAnswerButtons()
        }
        falseButton.setOnClickListener {
            checkAnswer(false)
            UpdateAnswerButtons()
        }
        nextButton.setOnClickListener{
            quizViewModel.moveToNext()
            updateQuestion()
            UpdateAnswerButtons()
        }
        prevButton.setOnClickListener{
            quizViewModel.moveToPrev()
            updateQuestion()
            UpdateAnswerButtons()
        }
        updateQuestion()
    }

    override fun onStart(){
        super.onStart()
        Log.d(TAG, "OnStart called")
    }

    override fun onResume(){
        super.onResume()
        Log.d(TAG, "OnResume called")
    }

    override fun onPause(){
        super.onPause()
        Log.d(TAG, "OnPause called")
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }

    override fun onStop(){
        super.onStop()
        Log.d(TAG, "OnStop called")
    }

    override fun onDestroy(){
        super.onDestroy()
        Log.d(TAG, "OnDestroy called")
    }

    private fun updateQuestion(){
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer:Boolean){
        quizViewModel.MakeCurrentQuestionAnswered()
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = if (userAnswer == correctAnswer){
            quizViewModel.questionsAnsweredCorrectly++
            R.string.correct_toast
        }
        else{
            R.string.incorrect_toast
        }
        if ((quizViewModel.questionsAnswered < quizViewModel.questionBankSize)){
            Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
        }
        else{
            val your_score = getString(R.string.your_score) + " ${quizViewModel.questionsAnsweredCorrectly}/${quizViewModel.questionsAnswered}"
            Toast.makeText(this, your_score, Toast.LENGTH_SHORT).show()
            quizViewModel.ResetQuestions()
        }
    }

    private fun UpdateAnswerButtons(){
        trueButton.isEnabled = !quizViewModel.currentQuestionIsAnswered
        falseButton.isEnabled = !quizViewModel.currentQuestionIsAnswered
    }
}
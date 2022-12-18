package com.bignerdranch.android.geomain

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

private const val SCORE = "score"
private const val MAX_SCORE = "maxScore"

class ResultActivity : AppCompatActivity() {
    private lateinit var resultText : TextView

    private var score = 0
    private var maxScore = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        score = intent.getIntExtra(SCORE, 0)
        maxScore = intent.getIntExtra(MAX_SCORE, 0)
        resultText = findViewById(R.id.resultText)
        resultText.text = getString(R.string.your_score) + " $score / $maxScore"
    }

    companion object{
        fun newIntent(packegeContext: Context, score: Int, maxScore: Int): Intent {
            return Intent(packegeContext, ResultActivity::class.java).apply {
                putExtra(SCORE, score)
                putExtra(MAX_SCORE, maxScore)
            }
        }
    }
}
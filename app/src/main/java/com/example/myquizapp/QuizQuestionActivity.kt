package com.example.myquizapp

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.myquizapp.databinding.ActivityQuizQuestionBinding

class QuizQuestionActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityQuizQuestionBinding
    private var mCurrentPosition: Int = 1
    private var mQuestionsList: ArrayList<Question>? = null
    private var mSelectedOptionPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mQuestionsList = Constants.getQuestions()

        setQuestion()
        binding.apply {
            tvOptionOne.setOnClickListener(this@QuizQuestionActivity)
            tvOptionTwo.setOnClickListener(this@QuizQuestionActivity)
            tvOptionThree.setOnClickListener(this@QuizQuestionActivity)
            tvOptionFour.setOnClickListener(this@QuizQuestionActivity)
            btnSubmit.setOnClickListener(this@QuizQuestionActivity)
        }

    }

    private fun setQuestion() {
        val question: Question = mQuestionsList!![mCurrentPosition - 1]
        defaultOptionsView()

        binding.apply {
            progressBar.progress = mCurrentPosition
            tvProgress.text = "$mCurrentPosition/${progressBar.max}"
            ivImage.setImageResource(question.image)
            tvQuestion.text = question.question
            tvOptionOne.text = question.optionOne
            tvOptionTwo.text = question.optionTwo
            tvOptionThree.text = question.optionThree
            tvOptionFour.text = question.optionFour

            if (mCurrentPosition == mQuestionsList!!.size) {
                btnSubmit.text = "Finish"
            } else {
                btnSubmit.text = "Submit"
            }

        }
    }

    private fun defaultOptionsView() {
        val options = ArrayList<TextView>()
        binding.apply {
            tvOptionOne.let {
                options.add(0, it)
            }
            tvOptionTwo.let {
                options.add(1, it)
            }
            tvOptionThree.let {
                options.add(2, it)
            }
            tvOptionFour.let {
                options.add(3, it)
            }
        }

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this@QuizQuestionActivity,
                R.drawable.default_option_border_bg
            )
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {
        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNum
        tv.apply {
            setTextColor(Color.parseColor("#363A43"))
            setTypeface(tv.typeface, Typeface.BOLD)
            background = ContextCompat.getDrawable(
                this@QuizQuestionActivity,
                R.drawable.selected_option_border_bg
            )
        }
    }

    override fun onClick(view: View?) {

        when (view?.id) {
            R.id.tv_option_one -> {
                selectedOptionView(binding.tvOptionOne, 1)
            }
            R.id.tv_option_two -> {
                selectedOptionView(binding.tvOptionTwo, 2)
            }
            R.id.tv_option_three -> {
                selectedOptionView(binding.tvOptionThree, 3)
            }
            R.id.tv_option_four -> {
                selectedOptionView(binding.tvOptionFour, 4)
            }
            R.id.btn_submit -> {
                if (mSelectedOptionPosition == 0) {
                    mCurrentPosition++

                    when {
                        mCurrentPosition <= mQuestionsList!!.size -> {
                            setQuestion()
                        }
                        else -> {
                            Toast.makeText(
                                this,
                                "You have successfully completed the quiz.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    val question = mQuestionsList?.get(mCurrentPosition - 1)

                    // This is to check if the answer is wrong
                    if (question!!.correctAnswer != mSelectedOptionPosition) {
                    }
                    answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)

                    // This is for correct answer
                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                    if (mCurrentPosition == mQuestionsList!!.size) {
                        binding.btnSubmit.text = "FINISH"
                    } else {
                        binding.btnSubmit.text = "GO TO NEXT QUESTION"
                    }

                    mSelectedOptionPosition = 0
                }
            }
        }

    }

    private fun answerView(answer: Int, drawableView: Int) {
        when (answer) {
            1 -> {
                binding.tvOptionOne.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            2 -> {
                binding.tvOptionTwo.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            3 -> {
                binding.tvOptionThree.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            4 -> {
                binding.tvOptionFour.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
        }
    }
}
package com.timwe.tti2sdk.ui.missions.daily.quiz

import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.data.entity.Answer
import com.timwe.tti2sdk.data.entity.Mission2
import com.timwe.tti2sdk.data.entity.Quiz
import com.timwe.tti2sdk.data.entity.generateQuiz
import com.timwe.tti2sdk.databinding.ActivityQuizBinding
import com.timwe.tti2sdk.ui.BaseActivity
import com.timwe.tti2sdk.ui.missions.daily.DailyViewModel
import com.timwe.utils.onDebouncedListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class QuizActivity(): BaseActivity() {

    private lateinit var binding : ActivityQuizBinding
    private val viewModel: DailyViewModel by viewModel()
    private var groupMissionId: Long = 0L
    private lateinit var answerAdapter: AnswerAdapter
    private var questionPosition: Int = 0
    private lateinit var quizTemp: Quiz

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
    }

    override fun onResume() {
        super.onResume()
        setupListeners()
        setupObservers()
    }

    override fun onBackPressed() {
        onBackPressedDispatcher.onBackPressed()
        finish()
    }

    private fun setupView(){
        val gson = Gson()
        val dataReceivedJson = intent.getStringExtra("MISSION")
        val mission = gson.fromJson(dataReceivedJson, Mission2::class.java)

        binding.btnAbandon.bringToFront()
        binding.btnNextQuestion.bringToFront()

        if(mission.quiz == null){
            loadAnswers(quiz = generateQuiz(), position = questionPosition)
            quizTemp = generateQuiz()
        }else{
            loadAnswers(quiz = mission.quiz, position = questionPosition)
            quizTemp = mission.quiz
        }
    }

    private fun setupListeners(){
        binding.btnBackQuiz.onDebouncedListener {
            onBackPressedDispatcher.onBackPressed()
            finish()
        }

        binding.btnAbandon.onDebouncedListener {
            onBackPressedDispatcher.onBackPressed()
            finish()
        }

        binding.btnNextQuestion.onDebouncedListener {
            if(quizTemp != null){
                viewModel.getNextQuestionQuiz(quiz = quizTemp, currentPosition = questionPosition)
            }
        }
    }

    private fun loadAnswers(quiz: Quiz, position: Int){
        binding.rvAnswersQuiz.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        answerAdapter = AnswerAdapter(
            context = this,
            data = quiz.questions[position].answers,
            mGlide = Glide.with(this),
            itemListener = singleClick
        )
        binding.rvAnswersQuiz.adapter = answerAdapter
        binding.txtQuestionNumber.text = getString(R.string.txt_question_number, (position+1).toString(), quiz.questions.size.toString() ?: 0.toString())
        binding.txtQuestionQuiz.text = quiz.questions[position].question

        Glide.with(this)
            .load(quiz.questions[position].imageUrl)
            .into(binding.imgMainQuiz)

        binding.quizPosition.progress = 25

    }

    private val singleClick = { answer : Answer ->
        if(answer.correctAnswer){
            Toast.makeText(this, "Correct !!!", Toast.LENGTH_SHORT).show()
        }else{

        }
    }

    private fun setupObservers(){
        viewModel.goToNextQuestion.observe(this){
            if(it){
                loadAnswers(quiz = quizTemp, position = questionPosition + 1)
            }
        }
    }

}
package com.example.authenticationwithsocialsite


import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.authenticationwithsocialsite.databinding.FragmentGameBinding
import kotlinx.android.synthetic.main.fragment_game.*

/**
 * A simple [Fragment] subclass.
 */
class GameFragment : Fragment() {
    data class Question(
        val text: String,
        val answers: List<String>, val level: String
    )

    //lateinit var nextW: Button
    private val questions: MutableList<Question> = mutableListOf(
        Question(
            text = "What is Android Jetpack?",
            answers = listOf("all of these", "tools", "documentation", "libraries"), level = "Easy"
        ),
        Question(
            text = "Base class for Layout?",
            answers = listOf("ViewGroup", "ViewSet", "ViewCollection", "ViewRoot"), level = "Easy"
        ),
        Question(
            text = "Layout for complex Screens?",
            answers = listOf("ConstraintLayout", "GridLayout", "LinearLayout", "FrameLayout"),
            level = "Easy"
        ),
        Question(
            text = "Pushing structured data into a Layout?",
            answers = listOf("Data Binding", "Data Pushing", "Set Text", "OnClick"),
            level = "Medium"
        ),
        Question(
            text = "Inflate layout in fragments?",
            answers = listOf(
                "onCreateView",
                "onActivityCreated",
                "onCreateLayout",
                "onInflateLayout"
            ), level = "Medium"
        ),
        Question(
            text = "Build system for Android?",
            answers = listOf("Gradle", "Graddle", "Grodle", "Groyle"), level = "Medium"
        ),
        Question(
            text = "Android vector format?",
            answers = listOf(
                "VectorDrawable",
                "AndroidVectorDrawable",
                "DrawableVector",
                "AndroidVector"
            ), level = "Hard"
        ),
        Question(
            text = "Android Navigation Component?",
            answers = listOf("NavController", "NavCentral", "NavMaster", "NavSwitcher"),
            level = "Hard"
        ),
        Question(
            text = "Registers app with launcher?",
            answers = listOf("intent-filter", "app-registry", "launcher-registry", "app-launcher"),
            level = "Hard"
        ),
        Question(
            text = "Mark a layout for Data Binding?",
            answers = listOf("<layout>", "<binding>", "<data-binding>", "<dbinding>"),
            level = "Easy"
        )
    )
    private val questionsmedium:MutableList<Question> = mutableListOf(
        Question(
            text = "What is Android Jetpack Medium?",
            answers = listOf("all of these", "tools", "documentation", "libraries"), level = "Easy"
        ),
        Question(
            text = "Base class for Layout? Medium",
            answers = listOf("ViewGroup", "ViewSet", "ViewCollection", "ViewRoot"), level = "Easy"
        ),
        Question(
            text = "Layout for complex Screens? Medium",
            answers = listOf("ConstraintLayout", "GridLayout", "LinearLayout", "FrameLayout"),
            level = "Easy"
        ),
        Question(
            text = "Pushing structured data into a Layout? Medium",
            answers = listOf("Data Binding", "Data Pushing", "Set Text", "OnClick"),
            level = "Medium"
        ),
        Question(
            text = "Inflate layout in fragments? Medium",
            answers = listOf(
                "onCreateView",
                "onActivityCreated",
                "onCreateLayout",
                "onInflateLayout"
            ), level = "Medium"
        ),
        Question(
            text = "Build system for Android? Medium",
            answers = listOf("Gradle", "Graddle", "Grodle", "Groyle"), level = "Medium"
        ),
        Question(
            text = "Android vector format? Medium",
            answers = listOf(
                "VectorDrawable",
                "AndroidVectorDrawable",
                "DrawableVector",
                "AndroidVector"
            ), level = "Hard"
        ),
        Question(
            text = "Android Navigation Component? Medium",
            answers = listOf("NavController", "NavCentral", "NavMaster", "NavSwitcher"),
            level = "Hard"
        ),
        Question(
            text = "Registers app with launcher? Medium",
            answers = listOf("intent-filter", "app-registry", "launcher-registry", "app-launcher"),
            level = "Hard"
        ),
        Question(
            text = "Mark a layout for Data Binding? Medium",
            answers = listOf("<layout>", "<binding>", "<data-binding>", "<dbinding>"),
            level = "Easy"
        )
    )
    lateinit var binding: FragmentGameBinding
    lateinit var currentQuestion: Question
    lateinit var answers: MutableList<String>
    lateinit var levelWizeQuestion: MutableList<Question>
    private var questionIndex = 0
    private var score = 0
    //private val numQuestions = Math.min((levelWizeQuestion.size + 1) / 2, 3)
    private var level = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)
        val args = GameFragmentArgs.fromBundle(arguments!!)
        level = args.gameLevel
        // Select Question According to level
        selsectQuestionAccordingToLevel()
        // Shuffles the questions and sets the question index to the first question.
        randomizeQuestions()
        //val sp =
        // Bind this fragment class to the layout
        binding.game = this

        // Set the onClickListener for the submitButton
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.submitButton.setOnClickListener @Suppress("UNUSED_ANONYMOUS_PARAMETER")
        { view: View ->
            val checkedId = binding.questionRadioGroup.checkedRadioButtonId
            // Do nothing if nothing is checked (id == -1)
            if (-1 != checkedId) {
                var answerIndex = 0
                when (checkedId) {
                    R.id.secondAnswerRadioButton -> answerIndex = 1
                    R.id.thirdAnswerRadioButton -> answerIndex = 2
                    R.id.fourthAnswerRadioButton -> answerIndex = 3
                }
                // The first answer in the original question is always the correct one, so if our
                // answer matches, we have the correct answer.
                if (answers[answerIndex] == currentQuestion.answers[0]) {
                    score++
                    questionIndex++
                    // Advance to the next question
                    if (questionIndex < 3) {
                        currentQuestion = levelWizeQuestion[questionIndex]
                        setQuestion()
                        binding.invalidateAll()
                    } else {
                        // We've won!  Navigate to the gameWonFragment.
                        val sharePreference =
                            PreferenceManager.getDefaultSharedPreferences(activity)
                        //view.findNavController().navigate(R.id.action_gameFragment_to_gameWonFragment)
                        var editor = sharePreference?.edit()
                        editor?.putString("SCORE", score.toString())
                        editor?.commit()
                        view.findNavController().navigate(
                            GameFragmentDirections.actionGameFragmentToGameWonFragment(score)
                        )
                    }
                } else {
                    // Game over! A wrong answer sends us to the gameOverFragment.
//                    view.findNavController().navigate(R.id.action_gameFragment_to_gameOverFragment)
                    view.findNavController()
                        .navigate(GameFragmentDirections.actionGameFragmentToGameOverFragment(score))
                }
            }
        }

    }

    // randomize the questions and set the first question
    private fun randomizeQuestions() {
        //questions.shuffle()
        levelWizeQuestion.shuffle()
        questionIndex = 0
        setQuestion()
    }

    // Sets the question and randomizes the answers.  This only changes the data, not the UI.
    // Calling invalidateAll on the FragmentGameBinding updates the data.
    private fun setQuestion() {
        binding.levelText.text = level
        binding.scoreText.text = "Your Score: "+score
        currentQuestion = levelWizeQuestion[questionIndex]
        // randomize the answers into a copy of the array
        answers = currentQuestion.answers.toMutableList()
        // and shuffle them
        answers.shuffle()
        (activity as AppCompatActivity).supportActionBar?.title =
            getString(R.string.title_android_trivia_question, questionIndex + 1, 3)
    }

    private fun selsectQuestionAccordingToLevel() {
        //levelWizeQuestion = questions
        Toast.makeText(activity, "Level is "+level, Toast.LENGTH_LONG).show()
        if (level == "Easy") {
//            for (q in questions) {
//                if (q.level == "Easy") {
//                    levelWizeQuestion.add(q)
//                }
//            }
            levelWizeQuestion = questions
        } else if (level == "Medium") {
//            for (q in questions) {
//                if (q.level == "Medium") {
//                    levelWizeQuestion.add(q)
//                }
//            }
            levelWizeQuestion = questionsmedium
        } else {
//            for (q in questions) {
//                if (q.level == "Hard") {
//                    levelWizeQuestion.add(q)
//                }
//            }
            levelWizeQuestion = questionsmedium
        }
//        Log.d("QUESTIONS", levelWizeQuestion[0].toString())
//        if(levelWizeQuestion == null){
//            Toast.makeText(activity, "Question hasan't been initilized", Toast.LENGTH_SHORT).show()
//        }else{
            //levelWizeQuestion = questions
        //}
    }
}

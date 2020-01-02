package com.example.authenticationwithsocialsite


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.authenticationwithsocialsite.databinding.FragmentGameOverBinding
import kotlinx.android.synthetic.main.fragment_game_won.*

/**
 * A simple [Fragment] subclass.
 */
class GameOverFragment : Fragment() {
    lateinit var binding: FragmentGameOverBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game_over, container, false)
        val args = GameOverFragmentArgs.fromBundle(arguments!!)
        //Toast.makeText(context, "NumCorrect: ${args.score}", Toast.LENGTH_LONG).show()
        binding.scoreShow?.text = "Your Score is "+args.score.toString()+" :("
        return binding.root
        //return inflater.inflate(R.layout.fragment_game_over, container, false)
    }


}

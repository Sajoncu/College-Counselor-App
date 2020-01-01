package com.example.authenticationwithsocialsite


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.authenticationwithsocialsite.databinding.FragmentTitleBinding

/**
 * A simple [Fragment] subclass.
 */
class TitleFragment : Fragment() {
    lateinit var binding: FragmentTitleBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_title, container, false)

        binding.playgame.setOnClickListener{
            // Navigation.findNavController(it).navigate(R.id.action_bacaryFragment_to_icecremeFragment)
            //var action = BacaryFragmentDirections.actionBacaryFragmentToIcecremeFragment(binding.editText.text.toString())
            //it.findNavController().navigate(action)
            Navigation.findNavController(it).navigate(R.id.action_titleFragment_to_gameFragment)
        }
        return binding.root
        //return inflater.inflate(R.layout.fragment_bacary, container, false)
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_title, container, false)
    }


}

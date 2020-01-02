package com.example.authenticationwithsocialsite

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.authenticationwithsocialsite.databinding.FragmentProfileBinding
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    val auth = FirebaseAuth.getInstance().currentUser
    lateinit var binding:FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_profile, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        val sharePreference = PreferenceManager.getDefaultSharedPreferences(activity)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (auth != null) {
            createUI()
        } else {
            startActivity(Intent(getActivity(), LoginActivity::class.java))
        }
    }

    fun createUI() {
        val list = auth?.providerData
        var providerData: String = ""
        list?.let {
            for (provider in list) {
                providerData = providerData + " " + provider.providerId
            }
        }

        auth?.let {
            txtName.text = auth.displayName
            txtEmail.text = auth.email
            txtPhone.text = auth.phoneNumber
            txtProvider.text = providerData
            Glide
                .with(this)
                .load(auth.photoUrl)
                .fitCenter()
                .placeholder(R.drawable.profilepic)
                .into(profile_image)
        }
        val sharePreference = PreferenceManager.getDefaultSharedPreferences(activity)
        val score = sharePreference.getString("SCORE", "")
        binding.showScore?.text = score.toString()
        btnLogOut.setOnClickListener {
            AuthUI.getInstance().signOut(requireContext())
                .addOnSuccessListener {
                    val intent = Intent(getActivity(), LoginActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                    Toast.makeText(getActivity(), "Successfully Log Out", Toast.LENGTH_SHORT).show()
                }
        }
    }


}

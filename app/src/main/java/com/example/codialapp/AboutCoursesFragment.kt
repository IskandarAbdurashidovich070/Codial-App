package com.example.codialapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.codialapp.Models.Data
import com.example.codialapp.databinding.FragmentAboutCoursesBinding

class AboutCoursesFragment : Fragment() {
    private lateinit var binding: FragmentAboutCoursesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutCoursesBinding.inflate(layoutInflater)

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.text.text = Data.text.about

        return binding.root
        }

}
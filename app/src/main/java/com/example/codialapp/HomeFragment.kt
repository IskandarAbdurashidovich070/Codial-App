package com.example.codialapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.codialapp.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
  private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        binding.coursesBtn.setOnClickListener {
            findNavController().navigate(R.id.coursesFragment)
        }

        binding.mentorsBtn.setOnClickListener {
            findNavController().navigate(R.id.mentorsFragment)
        }
        binding.groupsBtn.setOnClickListener {
            findNavController().navigate(R.id.groupsFragment)
        }
        return binding.root
    }
}
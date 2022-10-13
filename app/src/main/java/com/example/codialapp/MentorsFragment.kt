package com.example.codialapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.codialapp.DB.MyDbHelper
import com.example.codialapp.Models.Courses
import com.example.codialapp.Models.Data
import com.example.codialapp.adapters.Click
import com.example.codialapp.adapters.RvAdapter
import com.example.codialapp.databinding.FragmentMentorsBinding
import com.example.codialapp.databinding.ItemRvBinding


class MentorsFragment : Fragment(), Click {
    private lateinit var binding: FragmentMentorsBinding
    private lateinit var myDbHelper: MyDbHelper
    private lateinit var rvAdapter: RvAdapter
    private lateinit var list: ArrayList<Courses>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMentorsBinding.inflate(layoutInflater)

        myDbHelper = MyDbHelper(context)
        list = ArrayList()
        list.addAll(myDbHelper.getCourses())
        rvAdapter = RvAdapter(list, this)
        binding.rv.adapter = rvAdapter


        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }

    override fun onClick(courses: Courses, position: Int) {
        Data.num = position
        findNavController().navigate(R.id.addMentorsFragment)

    }

    override fun onLongClick(courses: Courses, position: Int) {

    }
}
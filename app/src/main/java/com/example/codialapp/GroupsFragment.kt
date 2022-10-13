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
import com.example.codialapp.databinding.FragmentGroupsBinding


class GroupsFragment : Fragment(), Click {
    private lateinit var binding: FragmentGroupsBinding
    private lateinit var myDbHelper: MyDbHelper
    private lateinit var list: ArrayList<Courses>
    private lateinit var rvAdapter: RvAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGroupsBinding.inflate(layoutInflater)

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
        Data.num1 = position
        findNavController().navigate(R.id.viewGroupsFragment)
    }

    override fun onLongClick(courses: Courses, position: Int) {
    }
}
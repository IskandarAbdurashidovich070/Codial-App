package com.example.codialapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.codialapp.DB.MyDbHelper
import com.example.codialapp.Models.Courses
import com.example.codialapp.Models.Data
import com.example.codialapp.adapters.Click
import com.example.codialapp.adapters.RvAdapter
import com.example.codialapp.databinding.FragmentCoursesBinding
import com.example.codialapp.databinding.ItemDialogBinding

class CoursesFragment : Fragment(), Click {
    private lateinit var binding: FragmentCoursesBinding
    private lateinit var myDbHelper: MyDbHelper
    private lateinit var list: ArrayList<Courses>
    private lateinit var rvAdapter: RvAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCoursesBinding.inflate(layoutInflater)

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        myDbHelper = MyDbHelper(context)
        list = ArrayList()
        list.addAll(myDbHelper.getCourses())
        rvAdapter = RvAdapter(list, this)
        binding.rv.adapter = rvAdapter


        binding.addBtn.setOnClickListener {
            val alertDialog = AlertDialog.Builder(binding.root.context).create()
            val itemDialogBinding = ItemDialogBinding.inflate(layoutInflater)
            alertDialog.setView(itemDialogBinding.root)
            alertDialog.show()


            itemDialogBinding.addBtn.setOnClickListener {
                val courses = Courses(
                    itemDialogBinding.edtName.text.toString(),
                    itemDialogBinding.edtAbout.text.toString()
                )
                myDbHelper.addCourses(courses)
                list.add(courses)
                rvAdapter.notifyItemInserted(list.size - 1)
                Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                alertDialog.cancel()
            }

        }



        return binding.root
    }

    override fun onClick(courses: Courses, position: Int) {
        Data.text = courses
        findNavController().navigate(R.id.aboutCoursesFragment)
    }

    override fun onLongClick(courses: Courses, position: Int) {
        myDbHelper.deleteCourses(courses)
        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
        list.removeAt(position)
        for (i in myDbHelper.getMentors()) {
            if (position == i.groups) myDbHelper.deleteMentors(i)
        }
        for (i in myDbHelper.getGroups()) {
            if (position == i.group) myDbHelper.deleteGroups(i)
        }
        for (i in myDbHelper.getStudents()) {
            if (position == i.groups) myDbHelper.deleteStudents(i)
        }
        rvAdapter.notifyItemRemoved(position)
    }
}
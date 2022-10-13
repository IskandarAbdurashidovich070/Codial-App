package com.example.codialapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.codialapp.DB.MyDbHelper
import com.example.codialapp.Models.Data
import com.example.codialapp.Models.Students
import com.example.codialapp.adapters.RvMentorsAdapter
import com.example.codialapp.databinding.DialogItemMentorsBinding
import com.example.codialapp.databinding.FragmentViewGroup2Binding
import com.example.codialapp.databinding.RvItemMentorsBinding

class ViewGroupFragment : Fragment(), ClickStudents {
    private lateinit var binding: FragmentViewGroup2Binding
    private lateinit var myDbHelper: MyDbHelper
    private lateinit var list: ArrayList<Students>
    private lateinit var rvStudents: RvAdapterStudents
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewGroup2Binding.inflate(layoutInflater)

        myDbHelper = MyDbHelper(context)
        list = ArrayList()
        for (i in myDbHelper.getStudents()) {
            if (i.groups == Data.num2) list.add(i)
        }
        rvStudents = RvAdapterStudents(list, this)
        binding.rv.adapter = rvStudents


        if (Data.close == 0) {
            binding.startButton.visibility = View.VISIBLE
            binding.startButton.setOnClickListener {
                binding.startButton.backgroundTintList = null
                binding.startButton.isClickable = false
                binding.startButton.text = "Kurs Boshlangan"
                Data.close = 1
                Data.name.start_group = 1
                myDbHelper.editGroups(Data.name)
            }
        } else {
            binding.startButton.visibility = View.GONE
            binding.startButton.backgroundTintList = null
            binding.startButton.isClickable = false
            binding.startButton.text = "Kurs Boshlangan"
        }

        binding.startButton.setOnClickListener {
//            binding.startButton.backgroundTintList = null
//            binding.startButton.isClickable = false
//            binding.startButton.text = "Kurs Boshlangan"
            Toast.makeText(context, "The lesson started", Toast.LENGTH_SHORT).show()
            binding.startButton.visibility = View.GONE
            binding.tvStarted.visibility = View.VISIBLE
            Data.close = 1
            Data.name.start_group = 1
            myDbHelper.editGroups(Data.name)
        }


        binding.addBtn.setOnClickListener {
            val dialog = AlertDialog.Builder(binding.root.context).create()
            val dialogItemMentorsBinding = DialogItemMentorsBinding.inflate(layoutInflater)
            dialog.setView(dialogItemMentorsBinding.root)
            dialog.show()

            dialogItemMentorsBinding.addBtn.setOnClickListener {
                val students = Students(
                    dialogItemMentorsBinding.edtName.text.toString(),
                    dialogItemMentorsBinding.edtAbout.text.toString(),
                    dialogItemMentorsBinding.edtNumber.text.toString(),
                    Data.num2
                )

                myDbHelper.addStudents(students)
                Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                var index = list.indexOf(students)
                findNavController().popBackStack()
                findNavController().navigate(R.id.viewGroupFragment2)
                dialog.cancel()
            }

        }

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.groupName.text = Data.name.name
        binding.groupDay.text = Data.name.days
        binding.groupTime.text = Data.name.time
        binding.groupMentor.text = Data.name.mentors?.name
        return binding.root
    }

    override fun delete(students: Students, position: Int) {
        myDbHelper.deleteStudents(students)
        var index = list.indexOf(students)
        list.removeAt(index)
        rvStudents.notifyItemRemoved(position)
    }

    override fun edit(students: Students, position: Int) {
        val dialog = AlertDialog.Builder(binding.root.context).create()
        val dialogItemMentorsBinding = DialogItemMentorsBinding.inflate(layoutInflater)
        dialog.setView(dialogItemMentorsBinding.root)
        dialogItemMentorsBinding.edtName.setText(students.name)
        dialogItemMentorsBinding.edtAbout.setText(students.surname)
        dialogItemMentorsBinding.edtNumber.setText(students.number)

        dialogItemMentorsBinding.addBtn.setOnClickListener {
            val name = dialogItemMentorsBinding.edtName.text.toString().trim()
            val surname = dialogItemMentorsBinding.edtAbout.text.toString().trim()
            val number = dialogItemMentorsBinding.edtNumber.text.toString().trim()

            if (name.isNotEmpty() && surname.isNotEmpty() && number.isNotEmpty()) {

                students.name = name
                students.number = number
                students.surname = number
                myDbHelper.editStudents(students)
                Toast.makeText(context, "Edited", Toast.LENGTH_SHORT).show()
            }
            rvStudents.notifyItemChanged(list.size - 1)
            dialog.cancel()
        }

        dialog.show()
    }
}

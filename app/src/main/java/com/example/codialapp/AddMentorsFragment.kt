package com.example.codialapp

import android.os.Binder
import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.codialapp.DB.MyDbHelper
import com.example.codialapp.Models.Data
import com.example.codialapp.Models.Mentors
import com.example.codialapp.adapters.Click1
import com.example.codialapp.adapters.RvMentorsAdapter
import com.example.codialapp.databinding.ActivityMainBinding.inflate
import com.example.codialapp.databinding.DialogDeleteItemBinding
import com.example.codialapp.databinding.DialogItemMentorsBinding
import com.example.codialapp.databinding.FragmentAddMentorsBinding
import com.example.codialapp.databinding.ItemDialogBinding
import com.example.codialapp.databinding.ItemRvBinding


class AddMentorsFragment : Fragment(), Click1 {
    private lateinit var binding: FragmentAddMentorsBinding
    private lateinit var myDbHelper: MyDbHelper
    private lateinit var list: ArrayList<Mentors>
    private lateinit var rvMentorsAdapter: RvMentorsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddMentorsBinding.inflate(layoutInflater)

        myDbHelper = MyDbHelper(context)
        list = ArrayList()
        for (i in myDbHelper.getMentors()){
            if (i.groups == Data.num) list.add(i)
        }
        rvMentorsAdapter = RvMentorsAdapter(list, this)

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.addBtn.setOnClickListener {
            var dialog = AlertDialog.Builder(binding.root.context).create()
            var itemdialog = DialogItemMentorsBinding.inflate(layoutInflater)
            dialog.setView(itemdialog.root)
            dialog.show()

            itemdialog.addBtn.setOnClickListener {
                var mentors = Mentors(itemdialog.edtName.text.toString(), itemdialog.edtAbout.text.toString(), itemdialog.edtNumber.text.toString(), Data.num)
                myDbHelper.addMentors(mentors)
                list.add(mentors)
                rvMentorsAdapter.notifyItemInserted(list.size-1)
                Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                dialog.cancel()
            }
        }

        binding.rv.adapter = rvMentorsAdapter
        return binding.root
    }

    override fun onClick(mentors: Mentors, position: Int) {
        val dialog = AlertDialog.Builder(binding.root.context).create()
        val itemMentorsBinding = DialogDeleteItemBinding.inflate(layoutInflater)
        dialog.setView(itemMentorsBinding.root)
        dialog.show()

        itemMentorsBinding.addBtn.setOnClickListener {
            myDbHelper.deleteMentors(mentors)
            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
            list.removeAt(position)
            rvMentorsAdapter.notifyItemRemoved(position)
            dialog.cancel()
        }
      itemMentorsBinding.noBtn.setOnClickListener {
          dialog.cancel()
      }

    }

    override fun onClick1(mentors: Mentors, position: Int) {
        var dialog = AlertDialog.Builder(binding.root.context).create()
        var itemdialog = DialogItemMentorsBinding.inflate(layoutInflater)
        dialog.setView(itemdialog.root)
        itemdialog.edtName.setText(mentors.name)
        itemdialog.edtAbout.setText(mentors.surname)
        itemdialog.edtNumber.setText(mentors.number)

        itemdialog.addBtn.setOnClickListener {
            val name = itemdialog.edtName.text.toString().trim()
            val number = itemdialog.edtNumber.text.toString().trim()
            val about = itemdialog.edtAbout.text.toString().trim()

            if (name.isNotEmpty() && number.isNotEmpty() && about.isNotEmpty()){
                mentors.name = name
                mentors.number = number
                mentors.surname = about
                myDbHelper.editMentors(mentors)
                Toast.makeText(context, "Edited", Toast.LENGTH_SHORT).show()
            }
            rvMentorsAdapter.notifyItemChanged(list.size-1)
            dialog.cancel()
        }

        dialog.show()


    }
}
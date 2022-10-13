package com.example.codialapp

import android.icu.text.Transliterator.Position
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.codialapp.DB.MyDbHelper
import com.example.codialapp.Models.Data
import com.example.codialapp.Models.Groups
import com.example.codialapp.adapters.ClickGroup
import com.example.codialapp.adapters.RvAdapterGroups
import com.example.codialapp.databinding.DialogItemMentorsBinding
import com.example.codialapp.databinding.FragmentItemPager1Binding
import com.example.codialapp.databinding.ItemDialogGroupBinding

class ItemPager1Fragment : Fragment(), ClickGroup {
    private lateinit var binding: FragmentItemPager1Binding
    private lateinit var myDbHelper: MyDbHelper
    private lateinit var rvAdapterGroups: RvAdapterGroups
    private lateinit var list: ArrayList<Groups>
    private lateinit var listTime : ArrayList<String>
    private lateinit var listDay: ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentItemPager1Binding.inflate(layoutInflater)




        listTime = ArrayList()
        listTime.add("12:00/14:00")
        listTime.add("14:00/16:00")
        listTime.add("16:00/18:00")
        listTime.add("18:00/20:00")

        listDay = ArrayList()
        listDay.add("Monday/Wednesday/Friday")
        listDay.add("Tuesday/Thursday/Saturday")


        myDbHelper = MyDbHelper(context)
        list = ArrayList()
        for (i in myDbHelper.getGroups()){
            if (i.group == Data.num1 && i.start_group == 0) list.add(i)
        }
        rvAdapterGroups = RvAdapterGroups(list, this)
        binding.rv.adapter = rvAdapterGroups


        return binding.root
    }

    override fun onClick(groups: Groups, position: Int) {
        Data.name = groups
        Data.num2 = position
        Data.group = groups
        findNavController().navigate(R.id.viewGroupFragment2)
    }

    override fun delete(groups: Groups, position: Int) {
        myDbHelper.deleteGroups(groups)
        var index = list.indexOf(groups)
        list.removeAt(index)
        for (i in myDbHelper.getStudents()) {
            if (position == i.groups) myDbHelper.deleteStudents(i)
        }
        rvAdapterGroups.notifyItemRemoved(position)
    }



    override fun edit(groups: Groups, position: Int) {

        var dialog = AlertDialog.Builder(binding.root.context).create()
        var itemdialog = ItemDialogGroupBinding.inflate(layoutInflater)
        dialog.setView(itemdialog.root)
        itemdialog.edtName.setText(groups.name)
        itemdialog.spinnerTime.selectedItem
        itemdialog.spinnerDay.selectedItem


        val listMentors = myDbHelper.getMentors()
        val listMentorsName = ArrayList<String>()
        listMentors.forEach {
            listMentorsName.add(it.name)
        }
        val arrayAdapter = ArrayAdapter<String>(
            binding.root.context, android.R.layout.simple_list_item_1, listMentorsName
        )

        itemdialog.spinner.adapter = arrayAdapter

        itemdialog.spinnerDay.adapter = ArrayAdapter(binding.root.context, android.R.layout.simple_list_item_1, listDay)
        itemdialog.spinnerTime.adapter = ArrayAdapter(binding.root.context, android.R.layout.simple_list_item_1, listTime)

        itemdialog.spinnerTime.setSelection(groups.selection.toString().toInt())
        itemdialog.spinnerDay.setSelection(groups.selection2.toString().toInt())
        itemdialog.spinner.setSelection(groups.selection3.toString().toInt())

        itemdialog.addBtn.setOnClickListener {
            val name = itemdialog.edtName.text.toString().trim()


            if (name.isNotEmpty() ){
                groups.name = name
                groups.time = listTime[itemdialog.spinnerTime.selectedItemPosition]
                groups.days = listDay[itemdialog.spinnerDay.selectedItemPosition]
                groups.mentors = listMentors[itemdialog.spinner.selectedItemPosition]
                groups.selection = itemdialog.spinnerTime.selectedItemPosition
                groups.selection2 = itemdialog.spinnerDay.selectedItemPosition
                groups.selection3 = itemdialog.spinner.selectedItemPosition

                myDbHelper.editGroups(groups)
                Toast.makeText(context, "Edited", Toast.LENGTH_SHORT).show()
            }
            rvAdapterGroups.notifyItemChanged(list.size-1)
            dialog.cancel()
        }

        dialog.show()
    }
}
package com.example.codialapp

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
import com.example.codialapp.adapters.MyViewPagerAdapter
import com.example.codialapp.adapters.RvAdapterGroups
import com.example.codialapp.databinding.FragmentViewGroupsBinding
import com.example.codialapp.databinding.ItemDialogBinding
import com.example.codialapp.databinding.ItemDialogGroupBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ViewGroupsFragment : Fragment() {
    private lateinit var binding: FragmentViewGroupsBinding
    private lateinit var myViewPagerAdapter: MyViewPagerAdapter
    private lateinit var myDbHelper: MyDbHelper
    private lateinit var list: ArrayList<Groups>
    private lateinit var listSpinner: ArrayList<String>
    private lateinit var listSpinner2: ArrayList<String>
    private lateinit var rvAdapterGroups: RvAdapterGroups
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewGroupsBinding.inflate(layoutInflater)

        listSpinner = ArrayList()
        listSpinner.add("12:00/14:00")
        listSpinner.add("14:00/16:00")
        listSpinner.add("16:00/18:00")
        listSpinner.add("18:00/20:00")

        listSpinner2 = ArrayList()
        listSpinner2.add("Monday/Wednesday/Friday")
        listSpinner2.add("Tuesday/Thursday/Saturday")

        myDbHelper = MyDbHelper(context)

        list = myDbHelper.getGroups() as ArrayList<Groups>
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.addBtn.setOnClickListener {
            val dialog = AlertDialog.Builder(binding.root.context).create()
            val itemDialogGroupBinding = ItemDialogGroupBinding.inflate(layoutInflater)
            dialog.setView(itemDialogGroupBinding.root)
            dialog.show()

            val listMentors = myDbHelper.getMentors()
            val listMentorsName = ArrayList<String>()
            listMentors.forEach {
                listMentorsName.add(it.name)
            }
            val arrayAdapter = ArrayAdapter<String>(
                binding.root.context, android.R.layout.simple_list_item_1, listMentorsName
            )
            itemDialogGroupBinding.spinner.adapter = arrayAdapter

            itemDialogGroupBinding.spinnerTime.adapter = ArrayAdapter<String>(binding.root.context, android.R.layout.simple_list_item_1, listSpinner)

            itemDialogGroupBinding.spinnerDay.adapter = ArrayAdapter<String>(binding.root.context, android.R.layout.simple_list_item_1, listSpinner2)




            itemDialogGroupBinding.addBtn.setOnClickListener {
                val groups = Groups(
                    itemDialogGroupBinding.edtName.text.toString(),
                    listSpinner[itemDialogGroupBinding.spinnerTime.selectedItemPosition],
                    listSpinner2[itemDialogGroupBinding.spinnerDay.selectedItemPosition],
                    listMentors[itemDialogGroupBinding.spinner.selectedItemPosition],
                    Data.num1,
                    itemDialogGroupBinding.spinnerTime.selectedItemPosition,
                    itemDialogGroupBinding.spinnerDay.selectedItemPosition,
                    itemDialogGroupBinding.spinner.selectedItemPosition,
                   0
                )
                myDbHelper.addGroups(groups)
                list.add(groups)
                findNavController().popBackStack()
                findNavController().navigate(R.id.viewGroupsFragment)
                Toast.makeText(context, "Save", Toast.LENGTH_SHORT).show()
                dialog.cancel()
            }

        }


        myViewPagerAdapter = MyViewPagerAdapter(this)

        binding.viewPager.adapter = myViewPagerAdapter

        binding.tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 0) {
                    binding.addBtn.visibility = View.VISIBLE
                } else {
                    binding.addBtn.visibility = View.GONE
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

        var list = ArrayList<String>()
        list.add("Ochilayotgan guruhlar")
        list.add("Ochilgan guruhlar")
        TabLayoutMediator(binding.tab, binding.viewPager) { tab, position ->
            tab.text = list[position]
        }.attach()

        return binding.root
    }

}
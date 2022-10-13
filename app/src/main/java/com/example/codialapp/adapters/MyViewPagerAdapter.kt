package com.example.codialapp.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.codialapp.ItemPager1Fragment
import com.example.codialapp.PagerItemFragment

class MyViewPagerAdapter(var fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
      return when(position){
           0 -> return ItemPager1Fragment()
          else -> PagerItemFragment()
      }

    }
}
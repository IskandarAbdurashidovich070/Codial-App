package com.example.codialapp.adapters

import com.example.codialapp.Models.Groups
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.codialapp.databinding.RvItemGroupBinding

class RvAdapterGroups(var list: List<Groups>, var clickGroup: ClickGroup) : RecyclerView.Adapter<RvAdapterGroups.Vh>() {

    inner class Vh(var rvItemGroupBinding: RvItemGroupBinding):RecyclerView.ViewHolder(rvItemGroupBinding.root){
        fun onBind(groups: Groups, position: Int ){
            rvItemGroupBinding.name.text = groups.name
            rvItemGroupBinding.view.setOnClickListener {
                clickGroup.onClick(groups, position)
            }
            rvItemGroupBinding.delete.setOnClickListener {
                clickGroup.delete(groups, position)
            }
            rvItemGroupBinding.edit.setOnClickListener {
                clickGroup.edit(groups, position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(RvItemGroupBinding.inflate(LayoutInflater.from(parent.context) , parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

}

interface ClickGroup{
    fun onClick(groups: Groups, position: Int)
    fun delete(groups: Groups, position: Int)
    fun edit(groups: Groups, position: Int)
}
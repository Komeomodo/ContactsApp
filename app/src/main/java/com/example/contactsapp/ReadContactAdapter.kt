package com.example.contactsapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsapp.fragments.ReadContactFragment
import kotlinx.android.synthetic.main.contact.view.*

class ReadContactAdapter(val clickListener: ReadContactFragment, private var recycleItems: List<ContactClass>):
    RecyclerView.Adapter<ReadContactAdapter.AdapterViewHolder>(){
    private var colors = listOf("#771AB6", "#e15a97", "#4b2840","#576490","#ef2d56","#38686a", "#2589bd")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        // Inflate the itemView
        var contactView = LayoutInflater.from(parent.context).inflate(R.layout.contact,parent, false)
        return AdapterViewHolder(contactView)
    }
    //this function get the number of items in our data source
    override fun getItemCount(): Int {
        return recycleItems.size
    }

    //  This function populate the view with data from our globalData
    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        var currentPosition = recycleItems[position]
        var color = colors.random()

        holder.contactName.text = currentPosition.name
//        holder.initialClick(recycleItems.get(position), clickListener)
        holder.contactLogo.text = currentPosition.name?.get(0).toString()
        holder.logo.setCardBackgroundColor(Color.parseColor(color))

    }

    // Provide a direct reference to each of the views within a data item
    inner class AdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var contactName: TextView = itemView.contactName
        var contactLogo: TextView = itemView.contactLogo
        var logo: CardView = itemView.cardView

    }



}
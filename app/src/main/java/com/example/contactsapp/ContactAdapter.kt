package com.example.contactsapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsapp.fragments.ContactListFragment
import kotlinx.android.synthetic.main.contact.view.*

class ContactAdapter(val clickListener: ContactListFragment):
    RecyclerView.Adapter<ContactAdapter.AdapterViewHolder>(){
    private var colors = listOf("#771AB6", "#e15a97", "#4b2840","#576490","#ef2d56","#38686a", "#2589bd")

    private var recycleItems = mutableListOf<ContactClass>()


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

//        holder.contactName.text = currentPosition.name
        holder.initialClick(recycleItems.get(position), clickListener)
        holder.contactLogo.text = currentPosition.name?.get(0).toString()
        holder.logo.setCardBackgroundColor(Color.parseColor(color))

    }

    fun setContact(recycleItems: List<ContactClass>){
        this.recycleItems = recycleItems as MutableList<ContactClass>
        notifyDataSetChanged()
    }

    fun addContact(contact: ContactClass){
        if (!recycleItems.contains(contact)){
            recycleItems.add(contact)
            notifyDataSetChanged()
        }
//        else{
//           val index = recycleItems.indexOf(contact)
//            recycleItems[index] = contact
//
//        }
//        notifyDataSetChanged()
    }

    // Provide a direct reference to each of the views within a data item
    inner class AdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var contactName: TextView = itemView.contactName
        var contactLogo: TextView = itemView.contactLogo
        var logo: CardView = itemView.cardView

        fun initialClick(recycleItems: ContactClass, action: onRecycleItemClickListener){
            contactName.text = recycleItems.name

            itemView.setOnClickListener{
                action.onItemClick(recycleItems)
            }

           }

    }

    interface onRecycleItemClickListener{
        fun onItemClick(recycleItems: ContactClass)
    }
}
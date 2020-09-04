package com.example.contactsapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactsapp.ContactClass
import com.example.contactsapp.R
import com.example.contactsapp.ContactAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.contactsapp.ContactViewModel
import kotlinx.android.synthetic.main.fragment_contact_list.*


class ContactListFragment : Fragment() , ContactAdapter.onRecycleItemClickListener {
    private  lateinit var viewModel: ContactViewModel

    private val adapter = ContactAdapter(this)

    private var data = mutableListOf<ContactClass>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_contact_list, container, false)

        val add_button = view.findViewById<ImageView>(R.id.addContactsIcon)

        add_button.setOnClickListener(View.OnClickListener {
            /**Initialize the first fragment**/
            var fragmentManager = activity?.supportFragmentManager?.beginTransaction()
            fragmentManager?.replace(R.id.fragment_container, AddContactFragment())?.addToBackStack(null)
            fragmentManager?.commit()

        })

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        contact_recycle_view.adapter = adapter

            viewModel.fetchContacts()

            viewModel.getRealTimeUpdates()

            viewModel.contacts.observe(viewLifecycleOwner, Observer {
                adapter.setContact(it)
            })

            viewModel.contact.observe(viewLifecycleOwner, Observer {
                adapter.addContact(it)
            })


        moreIcon.setOnClickListener{
            val popupMenu: PopupMenu = PopupMenu(requireActivity(), moreIcon)
            popupMenu.menuInflater.inflate(R.menu.menu,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
//                Toast.makeText(requireActivity(), "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()
                var fragmentManager = activity?.supportFragmentManager?.beginTransaction()
                fragmentManager?.replace(R.id.fragment_container, ReadContactFragment())?.addToBackStack(null)
                fragmentManager?.commit()

                true
            })
            popupMenu.show()

    }


//        this is to determine how the collection items is displayed
        contact_recycle_view?.layoutManager = LinearLayoutManager(view?.context)




    }


    override fun onItemClick(recycleItems: ContactClass) {
//        val name = recycleItems.name

        val bundle = Bundle()
        bundle.putString("name", recycleItems.name)
        bundle.putString("phoneNumber", recycleItems.phoneNum)
        bundle.putString("email", recycleItems.email)
        bundle.putString("id", recycleItems.id)
        var contactDetailsFragment = DetailsFragment(recycleItems)
        contactDetailsFragment.arguments = bundle
        var fragmentManager = activity?.supportFragmentManager?.beginTransaction()
        fragmentManager?.replace(R.id.fragment_container, contactDetailsFragment)
        fragmentManager?.commit()

    }

}
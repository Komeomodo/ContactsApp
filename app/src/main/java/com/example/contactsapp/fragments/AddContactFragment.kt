package com.example.contactsapp.fragments

import android.accounts.AccountManager.get
import android.content.ContentValues.TAG
import android.nfc.tech.NfcBarcode.get
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.contactsapp.ContactClass
import com.example.contactsapp.ContactViewModel
import com.example.contactsapp.R
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.Array.get
import java.util.EnumSet.of
import java.util.Optional.of
import java.util.OptionalDouble.of


class AddContactFragment : Fragment() {
    private lateinit var viewModel : ContactViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_contact, container, false)



        val cancel_button = view.findViewById<Button>(R.id.cancelButton)
            cancel_button.setOnClickListener{
                var fragmentManager = activity?.supportFragmentManager?.beginTransaction()
                fragmentManager?.replace(R.id.fragment_container, ContactListFragment())?.addToBackStack(null)
                fragmentManager?.commit()
            }



        val save_button = view.findViewById<Button>(R.id.saveButton)
            save_button.setOnClickListener {
                var name = view.findViewById<EditText>(R.id.nameInput).text.trim()
                var phoneNumber = view.findViewById<EditText>(R.id.phoneInput).text.trim()
                var email = view.findViewById<EditText>(R.id.emailInput).text.trim()

                if (TextUtils.isEmpty(name) && TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(email)) {
                    Toast.makeText(it.context, "Contact Discarded", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(it.context, "Contact added", Toast.LENGTH_SHORT).show()
                }

                var contact = ContactClass()
                contact.name = name.toString()
                contact.phoneNum = phoneNumber.toString()
                contact.email = email.toString()

                viewModel.saveContacts(contact)

//                Log.d(TAG, "onCreateView: ${name} ${phoneNumber} ${email}")
//                var fragmentManager = activity?.supportFragmentManager?.beginTransaction()
//                fragmentManager?.replace(R.id.fragment_container, ContactListFragment())?.addToBackStack(null)
//                fragmentManager?.commit()

                fragmentManager?.beginTransaction()?.apply {
                    replace(R.id.fragment_container, ContactListFragment())
                    addToBackStack(null)
                    commit()
                }



            }


       return view

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        viewModel.result.observe(viewLifecycleOwner, Observer {
            val message = if(it == null){
                " Contact added"
            }else{
                it.message
            }
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        })
    }


}
package com.example.contactsapp.fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.contactsapp.ContactClass
import com.example.contactsapp.ContactViewModel
import com.example.contactsapp.R
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_details.view.*
import kotlinx.android.synthetic.main.fragment_edit_contact.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditContactFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditContactFragment(private var contact: ContactClass) : Fragment() {
    private  lateinit var viewModel: ContactViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_add_contact, container, false)

        view.nameInput.setText(contact.name)
        view.phoneInput.setText(contact.phoneNum)
        view.emailInput.setText(contact.email)

        val cancel_button = view.findViewById<Button>(R.id.cancelButton)
        cancel_button.setOnClickListener{
            activity?.supportFragmentManager?.popBackStack()

        }

        val save_button = view.findViewById<Button>(R.id.saveButton)

        save_button.setOnClickListener {
            var name = view.findViewById<EditText>(R.id.nameInput).text.trim()
            var phoneNumber = view.findViewById<EditText>(R.id.phoneInput).text.trim()
            var email = view.findViewById<EditText>(R.id.emailInput).text.trim()

            if (TextUtils.isEmpty(name) && TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(email)) {
                Toast.makeText(it.context, "No update", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(it.context, "Update Successful", Toast.LENGTH_SHORT).show()
            }
//                Log.d(TAG, "onCreateView: ${name} ${phoneNumber} ${email}")


            contact.name = name.toString()
            contact.phoneNum = phoneNumber.toString()
            contact.email = email.toString()

            viewModel.updateContacts(contact)

            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.fragment_container, ContactListFragment())
                addToBackStack(null)
                commit()
            }
//            var fragmentManager = activity?.supportFragmentManager?.beginTransaction()
//            fragmentManager?.replace(R.id.fragment_container, ContactListFragment())?.addToBackStack(null)
//            fragmentManager?.commit()
        }

        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}
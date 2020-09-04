package com.example.contactsapp.fragments

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager


import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactsapp.ContactClass
import com.example.contactsapp.R
import com.example.contactsapp.ReadContactAdapter
import kotlinx.android.synthetic.main.fragment_contact_list.*
import kotlinx.android.synthetic.main.fragment_contact_list.contact_recycle_view
import kotlinx.android.synthetic.main.fragment_read_contact.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ReadContactFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReadContactFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var isPermissionGranted = false

    companion object {
        val PERMISSIONS_REQUEST_READ_CONTACTS = 100
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_read_contact, container, false)

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


         loadContacts()
        //val adapter = ReadContactAdapter(this, contacts)

           refresh.setOnClickListener {
               requestPermissions(
                   arrayOf(Manifest.permission.READ_CONTACTS),
                   PERMISSIONS_REQUEST_READ_CONTACTS
               )
           }

        //        this is to determine how the collection items is displayed

    }

    private fun populate(contacts: MutableList<ContactClass>) {
        val adapter = ReadContactAdapter(this, contacts)
        contact_recycle_view.adapter = adapter
        contact_recycle_view.layoutManager = LinearLayoutManager(view?.context)


    }

    private fun loadContacts(){

         if ( checkSelfPermission(
                requireActivity(),
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                
                arrayOf(Manifest.permission.READ_CONTACTS),
                PERMISSIONS_REQUEST_READ_CONTACTS
            )
            //callback onRequestPermissionsResult
          //   Log.i("TAGI", "$isPermissionGranted")
             if (isPermissionGranted) {
                 loadContacts()
             }
        } else {
             val contacts = getContacts()

             populate(contacts)
         }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        //if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PERMISSION_GRANTED) {
                val contacts = getContacts()
                 isPermissionGranted = true
                Log.i("TAGI", "Permission accepted")
                populate(contacts)
            } else {
                Log.i("YAFE", "Permission denied")
                Toast.makeText(activity, "Permission must be granted in order to display contacts information", Toast.LENGTH_SHORT).show()

            }

    }


    private fun getContacts(): MutableList<ContactClass> {
        val contactList  = mutableListOf<ContactClass>()

        val cursor = activity?.contentResolver?.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null,  null)

        if (cursor?.count!! > 0) {
            while (cursor.moveToNext()) {
                val id = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID))
                val name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                contactList.add(ContactClass(name))

            }
        } else {
            Toast.makeText(activity, "No contacts available!", Toast.LENGTH_SHORT).show()
        }
        cursor.close()
        return contactList
    }
}
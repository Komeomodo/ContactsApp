package com.example.contactsapp.fragments

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.contactsapp.ContactClass
import com.example.contactsapp.ContactViewModel
import com.example.contactsapp.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.fragment_details.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailsFragment(private var contact: ContactClass = ContactClass()) : Fragment() {
    private  lateinit var viewModel: ContactViewModel
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        val view = inflater.inflate(R.layout.fragment_details, container, false)
        viewModel = ViewModelProvider(this).get(ContactViewModel::class.java)


        val contactName = this.arguments?.getString("name")
        var phoneNumber = this.arguments?.getString("phoneNumber")
        var contactEmail = this.arguments?.getString("email")
        var id = this.arguments?.getString("id")

        Log.i(TAG, "onCreateView: gkgagsck"+ id)
        view.etname.text = contactName.toString()
        view.etphoneNumber.text = phoneNumber.toString()
        view.etemail.text = contactEmail.toString()


        val contactClass = ContactClass(contactName,phoneNumber,contactEmail,id)


        val edit_button = view.findViewById<ImageView>(R.id.edit)
                edit_button.setOnClickListener(View.OnClickListener {

                    fragmentManager?.beginTransaction()?.apply {
                        replace(R.id.fragment_container, EditContactFragment(contactClass))
                        addToBackStack(null)
                        commit()
                    }
        })

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        delete.setOnClickListener {
            Toast.makeText(activity, "DELETE Button Clicked", Toast.LENGTH_SHORT).show()
            viewModel.deleteContacts(contact)
//            val fragmentTransaction: FragmentTransaction =
//            val myFragment = DetailsFragment()
//            fragmentTransaction.remove(myFragment).commit()

           // getActivity()?.getFragmentManager()?.beginTransaction()?.remove()
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.fragment_container, ContactListFragment())
                addToBackStack(null)
                commit()
            }

        }

        backButton.setOnClickListener(View.OnClickListener {
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.fragment_container, ContactListFragment())
                addToBackStack(null)
                commit()
            }
        })


        phoneLogo.setOnClickListener(View.OnClickListener {
            checkPermission()
        })

        share.setOnClickListener(View.OnClickListener {
            val sendIntent = Intent()
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(Intent.EXTRA_TEXT, contact.phoneNum)
                sendIntent.type = "text/plain"
                startActivity(sendIntent)
        })

    }

    private fun callPhone(){
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:${contact.phoneNum}"))
        startActivity(intent)
    }

    // Function to check and request permission.
    fun checkPermission() {

        if (ContextCompat.checkSelfPermission(requireActivity(),
                Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                    Manifest.permission.CALL_PHONE)) {
                Toast.makeText(activity, "Please grant ContactsApp permission to call.", Toast.LENGTH_SHORT).show()
                checkPermission()

            } else {
                ActivityCompat.requestPermissions(requireActivity(),
                    arrayOf(Manifest.permission.CALL_PHONE),
                    42)
            }
        } else {
            // Permission has already been granted
            callPhone()
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == 42) {
            // If request is cancelled, the result arrays are empty.
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // permission was granted, yay!
                callPhone()
            } else {
                // permission denied,  Disable the
                // functionality
            }
            return
        }
    }

}
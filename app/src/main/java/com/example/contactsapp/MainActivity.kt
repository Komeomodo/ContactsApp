package com.example.contactsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import com.example.contactsapp.fragments.ContactListFragment

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**Initialize the first fragment**/
        var fragmentManager = supportFragmentManager.beginTransaction()
        fragmentManager.replace(R.id.fragment_container, ContactListFragment())
        fragmentManager.addToBackStack(null)
        fragmentManager.commit()

    }


}
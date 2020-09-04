package com.example.contactsapp

import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import java.lang.Exception

class ContactViewModel: ViewModel() {
    private val dbContact = FirebaseDatabase.getInstance().getReference(NODE)

    private val _contact = MutableLiveData<ContactClass>()
        val contact: LiveData<ContactClass>
        get() = _contact

//
    private val _contacts = MutableLiveData<List<ContactClass>>()
    val contacts: LiveData<List<ContactClass>>
        get() = _contacts


//
    private val _result = MutableLiveData<Exception?>()
        val result: LiveData<Exception?>
                get() = _result

    fun saveContacts(contact: ContactClass){
        contact.id = dbContact.push().key

        dbContact.child(contact.id!!).setValue(contact)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    _result.value = null
                }else{
                    _result.value = it.exception
                }
            }
    }


    fun updateContacts(contact: ContactClass){

        dbContact.child(contact.id!!).setValue(contact)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    _result.value = null
                }else{
                    _result.value = it.exception
                }
            }
    }


    fun deleteContacts(contact: ContactClass){

        dbContact.child(contact.id!!).setValue(null)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    _result.value = null
                }else{
                    _result.value = it.exception
                }
            }
    }
//    fun fetchContacts(){
//        dbContact.addListenerForSingleValueEvent(object: ValueEventListener{
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if (snapshot.exists()){
//                    val contactsView = mutableListOf<ContactClass>()
//                    for (contactSnapshot in snapshot.children){
//                        val contact = contactSnapshot.getValue(ContactClass::class.java)
//                        contact?.id = contactSnapshot.key
//                        contact?.let{contactsView.add(it)}
//
//                    }
//
//                    _contacts.value = contactsView
//                }
//            }
//
//        })
//    }

    private val childEventListener = object: ChildEventListener{
        override fun onCancelled(error: DatabaseError) {

        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            val contact = snapshot.getValue(ContactClass::class.java)
            contact?.id = snapshot.key
            _contact.value = contact
        }

        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val contact = snapshot.getValue(ContactClass::class.java)
            contact?.id = snapshot.key
            _contact.value = contact
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {

        }

    }

    fun getRealTimeUpdates(){
        dbContact.addChildEventListener(childEventListener)
    }

    fun fetchContacts(){
        dbContact.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val contactsView = mutableListOf<ContactClass>()
                    for (contactSnapshot in snapshot.children){
                        val contact = contactSnapshot.getValue(ContactClass::class.java)
                        contact?.id = contactSnapshot.key
                        contact?.let{contactsView.add(it)}

                    }

                    _contacts.value = contactsView
                }
            }

        })
    }

    override fun onCleared() {
        super.onCleared()
        dbContact.removeEventListener(childEventListener)
    }

}
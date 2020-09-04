package com.example.contactsapp

import com.google.firebase.database.Exclude

data class ContactClass (var name: String? = null,
                         var email:String? = null,
                         var phoneNum: String? = null,

                         @get:Exclude
                         var id: String? = null

){
    override fun equals(other: Any?): Boolean {
        return if (other is ContactClass) {
            other.id == id
        } else false
    }

    override fun hashCode(): Int {
        var result = name?.hashCode() ?: 0
        result = 31 * result + (email?.hashCode() ?: 0)
        result = 31 * result + (phoneNum?.hashCode() ?: 0)
        result = 31 * result + (id?.hashCode() ?: 0)
        return result
    }
}

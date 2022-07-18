package com.khayrul.contactlist.ui.contact.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.khayrul.contactlist.data.model.Contact
import com.khayrul.contactlist.data.repository.ContactRepository
import com.khayrul.contactlist.ui.contact.base.BaseContactViewModel
import java.lang.IllegalArgumentException

class EditContactViewModel(private val repository: ContactRepository): BaseContactViewModel() {

    fun onViewCreated(id: Int) {
        val response = repository.findContactById(id)
        response?.let {
            name.value = it.name
            phone.value = it.phone
        }
    }

    fun update(id: Int, contact: Contact) {
        if(name.value.isNullOrEmpty() || phone.value.isNullOrEmpty()) {
            //error
        } else {
            repository.updateContact(id, contact)
        }
    }

    class Provider(private val repository: ContactRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(EditContactViewModel::class.java)) {
                return EditContactViewModel(repository) as T
            }

            throw IllegalArgumentException("Invalid ViewModel")
        }
    }
}
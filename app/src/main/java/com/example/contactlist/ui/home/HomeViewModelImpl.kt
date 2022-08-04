package com.example.contactlist.ui.home

import androidx.lifecycle.*
import com.example.contactlist.data.model.BaseItem
import com.example.contactlist.data.model.Title
import com.example.contactlist.data.repository.ContactRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModelImpl @Inject constructor(val contactRepository: ContactRepositoryImpl): HomeViewModel, ViewModel() {
    private val _contacts: MutableLiveData<List<BaseItem>> = MutableLiveData()
    override val contacts: LiveData<List<BaseItem>> = _contacts

    override val emptyScreen: MutableLiveData<Boolean> = MutableLiveData(false)

    private val _refreshFinish: MutableSharedFlow<Unit> = MutableSharedFlow<Unit>()
    override val refreshFinish: SharedFlow<Unit> = _refreshFinish


    init {
        getContacts()
    }


    private fun getContacts() {
        viewModelScope.launch {
            val response = contactRepository.getContacts().sortedBy { it.name }
            _refreshFinish.emit(Unit)
            val tempList: MutableList<BaseItem> = mutableListOf()
            val len = response.size
            for (i in 1..len) {
                if(i == 1) {
                    val title = Title("${response[i - 1].name[0].uppercaseChar()}")
                    tempList.add(title)
                } else if(response[i - 1].name[0] != response[i - 2].name[0]) {
                    val title = Title("${response[i - 1].name[0].uppercaseChar()}")
                    tempList.add(title)
                }
                tempList.add(response[i - 1])
            }
            _contacts.value = tempList
            emptyScreen.value = _contacts.value.isNullOrEmpty()
        }
    }

    override fun onDeleteClicked(id: Int) {
        viewModelScope.launch {
            contactRepository.deleteContact(id)
            refresh()
        }
    }

    override fun refresh() {
        getContacts()
    }

}
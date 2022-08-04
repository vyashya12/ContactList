package com.example.contactlist.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.contactlist.data.model.BaseItem
import kotlinx.coroutines.flow.SharedFlow

interface HomeViewModel {
    val contacts: LiveData<List<BaseItem>>
    val emptyScreen: MutableLiveData<Boolean>
    val refreshFinish: SharedFlow<Unit>
    fun onDeleteClicked(id: Int)
    fun refresh()
}
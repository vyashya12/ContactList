package com.example.contactlist.ui.contact.base

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

interface BaseContactViewModel {
    val name: MutableLiveData<String>
    val phone: MutableLiveData<String>

    val _finish: MutableSharedFlow<Unit>
    val finish: SharedFlow<Unit>

    val _error: MutableSharedFlow<String>
    val error: SharedFlow<String>
}
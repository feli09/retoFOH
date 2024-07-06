package com.example.retofoh.ui.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retofoh.data.repository.CandyRepository
import com.example.retofoh.ui.satate.ViewState
import kotlinx.coroutines.launch

class CandyViewModel() : ViewModel() {

    private val repository = CandyRepository()

    private var _eventState = MutableLiveData<ViewState>()
    var eventState: LiveData<ViewState> = _eventState


    fun getCandys(){
        viewModelScope.launch {
            _eventState.value = ViewState.ListCandy(repository.getCandy())
        }
    }

}
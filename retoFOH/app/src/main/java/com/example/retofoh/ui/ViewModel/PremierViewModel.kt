package com.example.retofoh.ui.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retofoh.data.repository.PremiersRepository
import com.example.retofoh.ui.satate.ViewState
import kotlinx.coroutines.launch

class PremierViewModel() : ViewModel() {

    private val repository = PremiersRepository()

    private var _eventState = MutableLiveData<ViewState>()
    var eventState: LiveData<ViewState> = _eventState


    fun getPremier(){
        viewModelScope.launch {
            _eventState.value = ViewState.ListMovie(repository.getPremiere())
        }
    }

}
package com.example.retofoh.ui.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retofoh.data.repository.PayRepository
import com.example.retofoh.data.repository.PayURepository
import com.example.retofoh.domain.model.Complete
import com.example.retofoh.domain.model.PaymentTransaction
import com.example.retofoh.ui.satate.ViewState
import kotlinx.coroutines.launch

class PayViewModel() : ViewModel() {

    private val repository = PayRepository()
    private val payURepository = PayURepository()

    private var _eventState = MutableLiveData<ViewState>()
    var eventState: LiveData<ViewState> = _eventState


    fun potComplete(complete: Complete){
        _eventState.value = ViewState.ShowLoader
        viewModelScope.launch {
            _eventState.value = ViewState.responseComplete(repository.postComplete(complete))
        }
        _eventState.value = ViewState.HideLoader
    }

    fun potPayupaymentTransaction(paymentTransaction: PaymentTransaction){
        _eventState.value = ViewState.ShowLoader
        viewModelScope.launch {
            _eventState.value = ViewState.responsePayU(payURepository.postPayU(paymentTransaction))
        }
        _eventState.value = ViewState.HideLoader
    }

}
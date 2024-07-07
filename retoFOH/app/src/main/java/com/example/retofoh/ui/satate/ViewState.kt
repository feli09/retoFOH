package com.example.retofoh.ui.satate

import com.example.retofoh.domain.model.Combo
import com.example.retofoh.domain.model.PaymentResponse
import com.example.retofoh.domain.model.ReponseComplete
import com.example.retofoh.domain.model.movie

sealed class ViewState {

    object ShowLoader : ViewState()
    object HideLoader : ViewState()
    data class ListMovie(val listMovie: List<movie>) : ViewState()
    data class ListCandy(val listCandy: List<Combo>) : ViewState()
    data class responseComplete(val reponseComplete: ReponseComplete) : ViewState()
    data class responsePayU(val paymentResponse: PaymentResponse) : ViewState()
}

package com.example.retofoh.ui.satate

import com.example.retofoh.domain.model.Combo
import com.example.retofoh.domain.model.movie

sealed class ViewState {
    data class ListMovie(val listMovie: List<movie>) : ViewState()
    data class ListCandy(val listCandy: List<Combo>) : ViewState()
}

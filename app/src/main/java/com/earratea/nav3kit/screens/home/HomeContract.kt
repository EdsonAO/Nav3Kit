package com.earratea.nav3kit.screens.home

object HomeContract {
    sealed interface Event {
        data object OnNavigateToDetail : Event
        data object OnNavigateToProfile : Event
    }
}

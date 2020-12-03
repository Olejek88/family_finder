package ru.shtrm.familyfinder.ui.base.interactor

interface MVPInteractor {

    fun isUserLoggedIn(): Boolean

    fun performUserLogout()
}
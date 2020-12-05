package ru.shtrm.familyfinder.data.network

import ru.shtrm.familyfinder.BuildConfig

object ApiEndPoint {

    val ENDPOINT_SERVER_LOGIN = BuildConfig.BASE_URL + "login"
    val ENDPOINT_LOGOUT = BuildConfig.BASE_URL + "logout"
    val ENDPOINT_SERVER_REGISTER = BuildConfig.BASE_URL + "register"

}
package ru.shtrm.familyfinder.data.network

import ru.shtrm.familyfinder.BuildConfig

object ApiEndPoint {

    const val ENDPOINT_SERVER_LOGIN = BuildConfig.BASE_URL + "login"
    const val ENDPOINT_LOGOUT = BuildConfig.BASE_URL + "logout"
    const val ENDPOINT_SERVER_REGISTER = BuildConfig.BASE_URL + "register"
    const val ENDPOINT_SERVER_TOKEN = BuildConfig.BASE_URL + "token"
    const val ENDPOINT_SERVER_ROUTES_SEND = BuildConfig.BASE_URL + "routes/send"
    const val ENDPOINT_SERVER_USER_SEND = BuildConfig.BASE_URL + "user/upload"
    const val ENDPOINT_SERVER_USER_IMAGE_SEND = BuildConfig.BASE_URL + "user/image"
    const val ENDPOINT_SERVER_USERS = BuildConfig.BASE_URL + "family"
    const val ENDPOINT_SERVER_GET_IMAGE = BuildConfig.SITE_URL + "storage/users/"

    const val NOMINATIM = "https://nominatim.openstreetmap.org/reverse.php"
}
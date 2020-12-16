package ru.shtrm.familyfinder.data.database

class AuthorizedUser {
    var _id: Long? = 0
    var username: String? = ""
    var login: String? = ""
    var password: String = ""
    var image: String? = ""
    var location: String? = ""

    var token: String? = null

    fun reset() {
        login = null
        token = null
    }

    companion object {

        private var mInstance: AuthorizedUser? = null

        val instance: AuthorizedUser
            @Synchronized get() {
                if (mInstance == null) {
                    mInstance = AuthorizedUser()
                }
                return mInstance!!
            }
    }
}

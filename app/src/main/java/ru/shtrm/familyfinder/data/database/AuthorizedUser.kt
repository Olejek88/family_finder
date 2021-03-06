package ru.shtrm.familyfinder.data.database

class AuthorizedUser {
    var _id: Long? = 0
    var username: String? = ""
    var login: String? = ""
    var password: String = ""
    var image: String? = ""
    var location: String? = ""
    var isSent: Boolean = true
    var isImageSent: Boolean = true
    var isAcraInit: Boolean = false

    var token: String? = null

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

package ru.shtrm.familyfinder.data.database.repository.user

import java.util.Date

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import ru.shtrm.familyfinder.data.database.IDbObject

open
class User : RealmObject() {
    @PrimaryKey
    var _id: Long = 0
    var username: String = ""
    var login: String = ""
    var password: String = ""
    var image: String = ""
    var createdAt: Date = Date()
    var changedAt: Date = Date()

    val imageFile: String
        get() = image

    val imageFilePath: String
        get() {
            val dir: String
            dir = imageRoot
            return dir
        }

    fun getImageFileUrl(userName: String): String {
        return "/storage/$userName/$imageFilePath"
    }

    companion object {

        val imageRoot: String
            get() = "users"
    }
}

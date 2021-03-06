package ru.shtrm.familyfinder.data.database.repository.user

import io.realm.Realm
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class User : RealmObject() {
    @PrimaryKey
    var _id: Long = 0
    var username: String = ""
    var login: String = ""
    var password: String = ""
    var image: String = ""
    var location: String? = ""
    var lastLatitude: Double = 0.0
    var lastLongitude: Double = 0.0
    var createdAt: Date = Date()
    var changedAt: Date = Date()
    var isSent: Boolean = true

    val imageFile: String
        get() = image

    val imageFilePath: String
        get() {
            return imageRoot
        }

    companion object {

        fun getLastId(): Long {
            val realm = Realm.getDefaultInstance()
            var lastId = realm.where(User::class.java).max("_id")
            if (lastId == null) {
                lastId = 1
            }

            realm.close()
            return lastId.toLong()
        }

        val imageRoot: String
            get() = "users"
    }
}

package ru.shtrm.familyfinder.data.database.repository.route

import java.util.Date

import io.realm.Realm
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.kotlin.where

import ru.shtrm.familyfinder.data.database.ISend
import kotlin.jvm.java

open class Route : RealmObject(), ISend {
    @PrimaryKey
    override var _id: Long = 0
    var userUuid: String = ""
    var latitude: Double = 0.toDouble()
    var longitude: Double = 0.toDouble()
    var date: Date = Date()
    var isSent: Boolean = false

    override fun setSent(sent: Boolean) {
        this.isSent = sent
    }

    companion object {

        val lastId: Long
            get() {
                val realm = Realm.getDefaultInstance()

                var lastId = realm.where(Route::class.java).max("_id")
                if (lastId == null) {
                    lastId = 0
                }

                realm.close()
                return lastId.toLong()
            }
    }
}

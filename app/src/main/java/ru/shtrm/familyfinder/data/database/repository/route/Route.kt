package ru.shtrm.familyfinder.data.database.repository.route

import io.realm.RealmObject
import java.util.*

open class Route : RealmObject() {
    var userId: Long = 0
    var latitude: Double = 0.toDouble()
    var longitude: Double = 0.toDouble()
    var date: Date = Date()
    var isSent: Boolean = false
}

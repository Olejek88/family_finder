package ru.shtrm.familyfinder.data.database.repository.migration

import android.util.Log

import io.realm.DynamicRealm

class Migration2 : IMigration {

    override fun migration(realm: DynamicRealm) {
        Log.d(this.javaClass.simpleName, "from version 1")
        val schema = realm.schema
        val obj = schema.get("User")
        if (obj != null) {
            obj.addField("lastLatitude", Double::class.java)
            obj.addField("lastLongitude", Double::class.java)
            obj.addField("location", String::class.java)
        }
    }
}
